package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {
    public Plane(Point v0, Point v1, Point v2) {
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }

    public Vector getNormal() {
        return null;
    }
}
