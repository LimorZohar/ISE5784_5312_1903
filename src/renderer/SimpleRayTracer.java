package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

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

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = this._scene.geometries.findIntersections(ray);
        if (intersections == null)
            return this._scene.background;
        return calcColor(ray.findClosestPoint(intersections));
    }

    /**
     * Get the color of an intersection point
     *
     * @param point point of intersection
     * @return Color of the intersection point
     */
    private Color calcColor(Point point) {
        return this._scene.ambientLight.getIntensity();
    }
}
