package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    final double DELTA = 0.0000001;

    @Test
    public void testGetNormal() {
        Sphere sphere = new Sphere(new Point(0, 0, 0), 1.0);
        Point p = new Point(1, 0, 0);
        Vector expect = new Vector(1, 0, 0).normalize();
        // ============ Equivalence Partitions Tests ==============
        // test point on the sphere
        assertEquals(expect, sphere.getNormal(p), "getNormal() for Sphere did not return the expected normal");
    }
}