package lighting;

import primitives.Color;

/**
 * Abstract base class representing a light source in a scene.
 */
public abstract class Light {
    /**
     * The intensity of the light source, represented by a Color object.
     */
    final protected Color intensity;

    /**
     * Constructs a light source with a given intensity.
     *
     * @param intensity The intensity of the light source, represented by a color.
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Retrieves the intensity of the light source.
     *
     * @return The color representing the intensity of the light.
     */
    public Color getIntensity() {
        return intensity;
    }
}
