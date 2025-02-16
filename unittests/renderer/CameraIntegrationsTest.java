package renderer;

import org.junit.jupiter.api.*;
import primitives.*;
import geometries.*;
import scene.Scene;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for integration between Camera and various geometries.
 */
public class CameraIntegrationsTest {

    /**
     * Returns the number of intersection points between a camera and a geometry.
     *
     * @param camera   The camera object.
     * @param nX       Number of rows (int).
     * @param nY       Number of columns (int).
     * @param geometry The geometry object to intersect with the camera.
     * @return The number of intersection points.
     */
    private int countIntersectionsCameraGeometry(Camera camera, int nX, int nY, Intersectable geometry) {
        int count = 0;

        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                var intersections = geometry.findGeoIntersections(camera.constructRay(nX, nY, j, i));
                count += intersections == null ? 0 : intersections.size();
            }
        }

        // Return the number of points of intersection between the geometries and a ray from the camera.
        return count;
    }

    /**
     * Test method for Camera-ray-sphere intersection.
     */
    @Test
    public void CameraRaySphereIntegration() {
        Camera camera1 = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setImageWriter(new ImageWriter("Test", 1, 1))
                .setLocation(Point.ZERO)
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpSize(3d, 3d)
                .setVpDistance(1d)
                .build();

        Camera camera2 = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setImageWriter(new ImageWriter("Test", 1, 1))
                .setLocation(new Point(0, 0, 0.5))
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpSize(3d, 3d)
                .setVpDistance(1d)
                .build();

        //TC01: Sphere r=1 (2 intersections)
        assertEquals(2, countIntersectionsCameraGeometry(camera1, 3, 3,
                new Sphere(new Point(0, 0, -3), 1d)), "Bad number of intersections");

        //TC02: Sphere r=2.5 (18 intersections)
        assertEquals(18, countIntersectionsCameraGeometry(camera2, 3, 3,
                new Sphere(new Point(0, 0, -2.5), 2.5)), "Bad number of intersections");

        //TC03: Sphere r=2 (10 intersections)
        assertEquals(10, countIntersectionsCameraGeometry(camera2, 3, 3,
                new Sphere(new Point(0, 0, -2), 2d)), "Bad number of intersections");

        //TC04: Sphere r=4 (9 intersections)
        assertEquals(9, countIntersectionsCameraGeometry(camera2, 3, 3,
                new Sphere(new Point(0, 0, 1), 4d)), "Bad number of intersections");

        //TC05: Sphere r=0.5 (0 intersections)
        assertEquals(0, countIntersectionsCameraGeometry(camera1, 3, 3,
                new Sphere(new Point(0, 0, 1), 0.5)), "Bad number of intersections");
    }

    /**
     * Test method for Camera-ray-triangle intersection.
     */
    @Test
    public void CameraRayTriangleIntegration() {
        Camera camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setImageWriter(new ImageWriter("Test", 1, 1))
                .setLocation(Point.ZERO)
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpSize(3d, 3d)
                .setVpDistance(1d)
                .build();

        //TC01: Small triangle (1 intersection)
        assertEquals(1, countIntersectionsCameraGeometry(camera, 3, 3, new Triangle(new Point(1, -1, -2),
                new Point(-1, -1, -2), new Point(0, 1, -2))), "Bad number of intersections");

        //TC02: Large triangle (2 intersections)
        assertEquals(2, countIntersectionsCameraGeometry(camera, 3, 3, new Triangle(new Point(1, -1, -2),
                new Point(-1, -1, -2), new Point(0, 20, -2))), "Bad number of intersections");
    }

    /**
     * Test method for Camera-ray-plane intersection.
     */
    @Test
    public void CameraRayPlaneIntegration() {
        Camera camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setImageWriter(new ImageWriter("Test", 1, 1))
                .setLocation(Point.ZERO)
                .setDirection(new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setVpSize(3d, 3d)
                .setVpDistance(1d)
                .build();

        //TC01: The plane parallel to the View Plane (9 intersections)
        assertEquals(9, countIntersectionsCameraGeometry(camera, 3, 3, new Plane(new Point(0, 0, 5),
                new Vector(0, 0, 1))), "Bad number of intersections");

        //TC02: Diagonal plane to the View Plane (9 intersections)
        assertEquals(9, countIntersectionsCameraGeometry(camera, 3, 3, new Plane(new Point(0, 0, 5),
                new Vector(0, -1, 2))), "Bad number of intersections");

        //TC03: Diagonal plane with an obtuse angle to the View Plane (6 intersections)
        assertEquals(6, countIntersectionsCameraGeometry(camera, 3, 3, new Plane(new Point(0, 0, 2),
                new Vector(1, 1, 1))), "Bad number of intersections");

        // TC04: The plane behind the view plane (0 intersections)
        assertEquals(0, countIntersectionsCameraGeometry(camera, 3, 3, new Plane(new Point(0, 0, -4),
                new Vector(0, 0, 1))), "Bad number of intersections");
    }
}
