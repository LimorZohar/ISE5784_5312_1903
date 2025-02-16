package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * Testing Polygons
 *
 * @author Dan
 */
public class PolygonTests {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.00001;

    /**
     * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        assertDoesNotThrow(() -> new Polygon(new Point(0, 0, 1),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(-1, 1, 1)),
                "Failed constructing a correct polygon");

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0),
                        new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertix on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0, 0, 1)),
                "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0, 1, 0)),
                "Constructed a polygon with vertice on a side");

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts =
                {new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(-1, 1, 1)};
        Polygon pol = new Polygon(pts);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = pol.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Polygon's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), DELTA,
                    "Polygon's normal is not orthogonal to one of the edges");
    }

    /**
     * Test method for {@link Intersectable#findGeoIntersectionsHelper(Ray)}.
     */
    @Test
    public void testFindGeoIntersectionsHelper() {
        Polygon polygon = new Polygon(
                new Point(1, 0, 0),
                new Point(0, 1, 0),
                new Point(-2, 0, 0),
                new Point(0, -1, 0)
        );

        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersects the polygon
        List<GeoPoint> result = polygon.findGeoIntersections(new Ray(new Point(-0.5, -0.5, -1), new Vector(0.5, 0.5, 3)));

        assertEquals(1,
                result.size(),
                "Wrong number of points");


        //TC02:Ray outside against vertex
        assertNull(polygon.findGeoIntersections(new Ray(new Point(0, -2, 0),
                        new Vector(0, 0, 4))),
                "Ray isn't outside against vertex");

        //TC03: Ray outside against edge
        assertNull(polygon.findGeoIntersections(new Ray(new Point(-1, -1, 0),
                        new Vector(0, 0, 3))),
                "Ray isn't outside against edge");

        //TC04:Ray inside the polygon
        assertNull(polygon.findGeoIntersections(new Ray(new Point(0, 0, 0),
                        new Vector(-1, 0, 0))),
                "Ray  isn't inside the polygon");

        // ============ Boundary Values Tests =============
        //TC05: Ray On edge
        result = polygon.findGeoIntersections(new Ray(new Point(-2, 0, 3), new Vector(1.03d, 0.51d, -3)));
        assertEquals(1,
                result.size(),
                "Wrong number of points");


        ///TC06: Ray in vertex
        assertNull(polygon.findGeoIntersections(new Ray(new Point(0, 1, 0), new Vector(-2d, -1d, 3))),
                "Ray  isn't on vertex of the polygon");

        //TC07: Ray On edge's continuation
        assertNull(polygon.findGeoIntersections(new Ray(new Point(-1, 2, 0), new Vector(-1d, -2d, 3))),
                "Ray  isn't On edge's continuation");


    }
}
