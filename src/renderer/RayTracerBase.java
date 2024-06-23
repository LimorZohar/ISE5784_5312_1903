package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * An abstract class for combining a scene and its color
 */
public abstract class RayTracerBase {

    /**
     * field for a scene
     */
    protected Scene _scene;

    /**
     * trace the ray and calculate the rey's intersection point color
     * and any other object (or the background if the rey's intersection point
     * doesn't exist)
     * @param ray for the ray that intersects the point
     * @return Color for the pixel
     */
    abstract public Color traceRay(Ray ray);

    /**
     * constructor
     * @param scene A scene where the department is initialized
     */
    public RayTracerBase(Scene scene) {
        this._scene = scene;
    }
}
