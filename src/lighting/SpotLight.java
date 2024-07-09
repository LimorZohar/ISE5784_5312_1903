package lighting;

import primitives.*;

import static primitives.Util.*;

/**
 * spot light class
 * son of point light
 * like spot-light light
 */
public class SpotLight extends PointLight {
    /**
     * The direction vector of the spot light.
     */
    private final Vector direction;

    /**
     * The narrow beam factor for the spot light.
     */
    private double narrowBeam = 1; // Default value for a wide beam


    /**
     * constructor with parameters
     *
     * @param direction vector
     * @param intensity color
     * @param position  point
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * set the NarrowBeam
     *
     * @param beamAngle double
     * @return this
     */
    public SpotLight setNarrowBeam(double beamAngle) {
        this.narrowBeam = beamAngle;
        return this;
    }

    @Override
    public SpotLight setkC(double kC) {
        return (SpotLight) super.setkC(kC);
    }

    @Override
    public SpotLight setkL(double kL) {
        return (SpotLight) super.setkL(kL);
    }

    @Override
    public SpotLight setkQ(double kQ) {
        return (SpotLight) super.setkQ(kQ);
    }

    @Override
    public Color getIntensity(Point p) {
            double cos = alignZero(direction.dotProduct(getL(p)));
            if (alignZero(cos) <= 0) return Color.BLACK;
            return super.getIntensity(p).scale(narrowBeam == 1 ? cos : Math.pow(cos, narrowBeam));
    }
}
