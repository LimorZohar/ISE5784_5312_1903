package lighting;

import primitives.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * SpotLight class represents a spotlight in a scene, extending the functionality of a PointLight.
 */
public class SpotLight extends PointLight {

    /**
     * The direction vector of the spotlight.
     */
    private final Vector direction;
    private double narrowBeam = 1;   // Default value for a wide beam

    /**
     * Constructs a SpotLight with the given intensity, position, and direction.
     *
     * @param intensity The intensity of the light source, represented by a color.
     * @param position  The position of the light source.
     * @param direction The direction of the spotlight.
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
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
        if (narrowBeam == 1) { // when here is no narrowBeam
            double cos = alignZero(direction.dotProduct(getL(p)));
            if (isZero(cos) || cos < 0) return Color.BLACK;
            return super.getIntensity().scale(cos);
        } else {  // when here is narrowBeam
            double projection = direction.dotProduct(getL(p));
            if (projection <= 0) {
                return Color.BLACK;
            }
            double factor = Math.pow(projection, narrowBeam);
            return super.getIntensity(p).scale(factor);
        }
    }
}
