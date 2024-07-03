package lighting;

import primitives.*;


public class PointLight extends Light implements LightSource {
    protected Point position;
    private double kC = 1, kL = 0, kQ = 0;

    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;

    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;

    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        return getIntensity().scale(1/(kC+kL*d+kQ*d*d));
    }

    @Override
    public Vector getL(Point p) {
        if (p.equals(position))
            return null;
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point p){
        return position.distance(p);
    }

}
