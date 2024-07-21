package lighting;

import primitives.*;

/**
 * Interface representing a light source in a scene.
 * This interface defines the basic contract for all light sources.
 */
public interface LightSource {

    /**
     * Returns the intensity of the light emitted by the source at a specific point.
     *
     * @param p The point at which to evaluate the light intensity.
     * @return The intensity of the light as a Color object.
     */
    public Color getIntensity(Point p);

    /**
     * Returns the direction vector from the light source to a specific point.
     * <p>
     * Note: The light source should not be exactly at the point.
     *
     * @param p The point to which the direction vector points.
     * @return The direction vector as a Vector object.
     */
    public Vector getL(Point p);

    /**
     * Returns the distance from the light source to a specific point.
     *
     * @param point The point to which the distance is measured.
     * @return The distance from the light source to the specified point.
     */
    public double getDistance(Point point);
}
