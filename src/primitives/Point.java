package primitives;

public class Point {

    public static final Point ZERO = new Point(Double3.ZERO);

    final Double3 xyz;

    public Point(double x, double y, double z) {
        xyz = new Double3(x,y,z);
    }
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }
    public Vector subtract(Point p) {
        return new Vector(xyz.subtract(p.xyz));
    }
    public Point add(Vector v1) {
        return new Point(xyz.add(v1.xyz));
    }
    public double distanceSquared(Point p) {
         Vector diff = subtract(p);
        return(diff.xyz.d1  * diff.xyz.d1 +
                diff.xyz.d2 * diff.xyz.d2 +
                diff.xyz.d3 * diff.xyz.d3);
    }
    public double distance(Point p) {
        return Math.sqrt(distanceSquared(p));
    }



}
