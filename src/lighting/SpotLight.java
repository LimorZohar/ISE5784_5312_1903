package lighting;

import primitives.*;

public class SpotLight extends PointLight {
    private Vector direction;

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

}
