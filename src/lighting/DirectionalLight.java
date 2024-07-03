package lighting;

import primitives.*;

/**
 * Directional Light class
 * light of the sun
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector direction;

    protected DirectionalLight(Color intensity, Vector direction) {
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
        return direction.distance(p);
    }

}
