package lighting;

import primitives.*;

/**
 * Directional Light class represents a light source with a constant direction.
 * This type of light is similar to sunlight, which has parallel rays.
 */
public class DirectionalLight extends Light implements LightSource {
    /**
     * The direction of the light rays.
     */
    private Vector direction;

    /**
     * Constructs a DirectionalLight with the specified intensity and direction.
     *
     * @param intensity The intensity of the light source, represented by a color.
     * @param direction The direction of the light rays.
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }

    @Override
    public double getDistance(Point p) {
        return Double.POSITIVE_INFINITY;
    }
}
