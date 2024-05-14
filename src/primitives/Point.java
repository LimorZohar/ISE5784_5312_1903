package primitives;

/**
 * Represents a point in three-dimensional space.
 */
public class Point {
    /**
     * A static ZERO point representing the origin.
     */
    public static final Point ZERO = new Point(Double3.ZERO);

    /**
     * The coordinates of the point.
     */
    final protected Double3 xyz;

    /**
     * Constructs a new Point with the specified x, y, and z coordinates.
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param z The z-coordinate of the point.
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * Constructs a new Point from a Double3 object representing the coordinates.
     * @param xyz The Double3 object representing the coordinates of the point.
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Computes the vector from this point to another point.
     * @param p The other point to subtract from this point.
     * @return The vector from this point to the other point.
     */
    public Vector subtract(Point p) {
            if ((this.xyz.subtract(p.xyz)).equals(Double3.ZERO))
                throw new IllegalArgumentException("Result cannot be zero");
            return new Vector(xyz.subtract(p.xyz));
    }

    /**
     * Adds a vector to this point and returns the resulting point.
     * @param v1 The vector to add to this point.
     * @return The resulting point after adding the vector.
     */
    public Point add(Vector v1) {
        return new Point(xyz.add(v1.xyz));
    }

    /**
     * Computes the squared distance between this point and another point.
     * @param point The other point to calculate the distance to.
     * @return The squared distance between this point and the other point.
     */
    public double distanceSquared(Point point) {
        return ((this.xyz.d1 - point.xyz.d1) * (this.xyz.d1 - point.xyz.d1) +
                (this.xyz.d2 - point.xyz.d2) * (this.xyz.d2 - point.xyz.d2) +
                (this.xyz.d3 - point.xyz.d3) * (this.xyz.d3 - point.xyz.d3));
    }

    /**
     * Computes the distance between this point and another point.
     * @param p The other point to calculate the distance to.
     * @return The distance between this point and the other point.
     */
    public double distance(Point p) {
        return Math.sqrt(distanceSquared(p));
    }


    @Override
    public boolean equals(Object obj) {
        //return super.equals(obj);
        if(this == obj)return true;
        if(obj == null || getClass()!= obj.getClass())return false;
        Point p = (Point) obj;
        return xyz.equals(p.xyz);
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
