package renderer;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

/**
 * A class that inherits from the RayTracerBase class and implements the method
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * constructor
     *
     * @param scene A scene where the department is initialized
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Get the color of an intersection point
     *
     * @param point point of intersection
     * @return Color of the intersection point
     */
    private Color calcColor(GeoPoint point) {
        return this.scene.ambientLight.getIntensity().add(point.geometry.getEmission());
    }

    @Override
    public Color traceRay(Ray ray) {
        var intersections = this.scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        return calcColor(ray.findClosestGeoPoint(intersections));
    }

}
