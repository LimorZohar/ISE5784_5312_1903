package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import geometries.Intersectable.GeoPoint;

/**
 * Unit tests for geometries.Geometries class
 */
public class GeometriesTest {

    /**
     * Test method for {@link geometries.Geometries#findGeoIntersectionsHelper(Ray)}.
     */
    @Test
    public void findGeoIntersectionsHelper() {
        Geometries geometries = new Geometries();

        // =============== Boundary Values Tests ==================
        //TC01: empty geometries list
        assertNull(geometries.findGeoIntersections(new Ray(new Point(0.0, 1.0, 0.0), new Vector(1.0, 0.0, 5.0))));

        geometries.add(new Plane(new Point(1.0, 1.0, 0.0), new Vector(0.0, 0.0, 1.0)));
        geometries.add(new Triangle(new Point(1.0, 0.0, 0.0), new Point(0.0, 1.0, 0.0), new Point(0.0, 0.0, 1.0)));
        geometries.add(new Sphere(new Point(1.0, 0.0, 0.0), 1.0));
        //TC02: each geometry doesn't have intersection points
        assertNull(geometries.findGeoIntersections(new Ray(new Point(0.0, 0.0, 2.0), new Vector(0.0, -1.0, 0.0))));

        List<Intersectable.GeoPoint> points = geometries.findGeoIntersections(new Ray(new Point(0.0, 5.0, -1.0), new Vector(0.0, 0.0, 1.0)));
        //TC03: just one geometry has intersections point
        assertEquals(1, points.size());

        //TC04: all the geometries have intersection points
        Geometries geometries1 = new Geometries(
                new Sphere(new Point(0, 0, 2), 0.5),
                new Polygon(
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(-1, 0, 0),
                        new Point(0, -1, 0)
                ),
                new Triangle(
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(0, 0, 1)
                )
        );
        List<GeoPoint> result = geometries1.findGeoIntersections
                (new Ray(new Point(0.2, 0.2, -0.6), new Vector(0, 0, 1)));
        assertEquals(4,
                result.size(),
                "All geometries intersects");

        // ============ Equivalence Partitions Tests ==============
        //TC05: part of the geometries has intersection points
        assertEquals(2,
                geometries.findGeoIntersections(new Ray(new Point(1.0, 0.0, -1.0), new Vector(0.0, 0.0, 1.0))).size());
    }
}
