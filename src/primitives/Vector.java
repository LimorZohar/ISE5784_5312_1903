package primitives;

public class Vector extends Point {
    public Vector(double x, double y, double z) {
        super(x, y, z);

    }

    public Vector(Double3 double3) {
        super(double3);
    }

    public double lengthSquared() {
        return this.xyz.d1 * this.xyz.d1 + this.xyz.d2 * this.xyz.d2 + this.xyz.d3 * this.xyz.d3;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public int dotProduct(Vector v3) {
        return (int) (this.xyz.d1 * v3.xyz.d1 + this.xyz.d2 * v3.xyz.d2 + this.xyz.d3 * v3.xyz.d3);

    }

    public Vector crossProduct(Vector v2) {
        double x = this.xyz.d2 * v2.xyz.d3 - this.xyz.d3 * v2.xyz.d2;
        double y = this.xyz.d3 * v2.xyz.d1 - this.xyz.d1 * v2.xyz.d3;
        double z = this.xyz.d1 * v2.xyz.d2 - this.xyz.d2 * v2.xyz.d1;
        return new Vector(x, y, z);
    }

    public Point normalize() {
        double length = length();
        try {
            if (length != 0) {
                double x = this.xyz.d1 / length;
                double y = this.xyz.d2 / length;
                double z = this.xyz.d3 / length;
                return new Point(x, y, z);
            } else {
                throw new ArithmeticException("Cannot normalize a zero vector.");
            }
        } catch (ArithmeticException e) {
            System.err.println(e.getMessage());
            return null; // Or handle the exception as needed
        }
    }

}

