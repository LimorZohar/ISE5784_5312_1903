package lighting;

import primitives.*;

/**
 * PointLight class represents a point light source in a scene.
 * It extends the abstract class Light and implements the LightSource interface.
 */
public class PointLight extends Light implements LightSource {

    /**
     * The position of the point light source in 3D space.
     */
    protected Point position;

    /**
     * Constant attenuation factor (default value is 1).
     */
    private double kC = 1;

    /**
     * Linear attenuation factor (default value is 0).
     */
    private double kL = 0;

    /**
     * Quadratic attenuation factor (default value is 0).
     */
    private double kQ = 0;

    /**
     * Sets the constant attenuation factor.
     *
     * @param kC The constant attenuation factor to set.
     * @return This PointLight object with the updated constant attenuation factor.
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation factor.
     *
     * @param kL The linear attenuation factor to set.
     * @return This PointLight object with the updated linear attenuation factor.
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor.
     *
     * @param kQ The quadratic attenuation factor to set.
     * @return This PointLight object with the updated quadratic attenuation factor.
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Constructs a point light source with a specified intensity and position.
     *
     * @param intensity The intensity of the point light source, represented by a Color object.
     * @param position  The position of the point light source, represented by a Point object.
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Returns the intensity of the light emitted by the point light source at a specific point.
     * Takes into account the distance attenuation based on the position of the light source and the point.
     *
     * @param p The point at which to evaluate the light intensity.
     * @return The intensity of the light as a Color object.
     */
    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        return intensity.scale(1 / (kC + kL * d + kQ * d * d));
    }

    /**
     * Returns the direction vector from the point light source to a specific point.
     *
     * @param p The point to which the direction vector points.
     * @return The direction vector as a Vector object.
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    /**
     * Set the constant attenuation factor
     *
     * @param kc the constant attenuation factor
     * @return kc
     */
    public PointLight setKc(double kc) {
        this.kC = kc;
        return this;
    }

    /**
     * Set the linear attenuation factor
     *
     * @param kl the linear attenuation factor
     * @return kl
     */
    public PointLight setKl(double kl) {
        this.kL = kl;
        return this;
    }

    /**
     * Set the quadratic attenuation factor
     *
     * @param kq the quadratic attenuation factor
     * @return kq
     */
    public PointLight setKq(double kq) {
        this.kQ = kq;
        return this;
    }
    /**
     * Returns the distance between the point light source and a specific point.
     *
     * @param p The point to which the distance is measured.
     * @return The distance between the light source and the point.
     */
    @Override
    public double getDistance(Point p) {
        return position.distance(p);
    }

}
