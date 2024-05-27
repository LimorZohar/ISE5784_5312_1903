package primitives;

import static primitives.Util.isZero;

/**
 * Represents a vector in three-dimensional space, extending the Point class.
 */
public class Vector extends Point {
    /**
     * Constructs a new Vector with the specified x, y, and z coordinates.
     *
     * @param x The x-coordinate of the vector.
     * @param y The y-coordinate of the vector.
     * @param z The z-coordinate of the vector.
     * @throws IllegalArgumentException If all coordinates are zero (resulting in a zero vector).
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        // Check if all components are zero and throw an exception if so
        if (xyz.equals(Double3.ZERO)) throw new IllegalArgumentException("Vector components cannot all be zero");
    }

    /**
     * Constructs a new Vector from a Double3 object.
     *
     * @param double3 The Double3 object representing the vector.
     * @throws IllegalArgumentException If the Double3 object is equal to Double3.ZERO (resulting in a zero vector).
     */
    public Vector(Double3 double3) {
        super(double3);
        // Validate the Double3 object to prevent creating a zero vector unintentionally
        if (xyz.equals(Double3.ZERO)) throw new IllegalArgumentException("Vector cannot be zero");
    }

    /**
     * Returns the squared length of this vector.
     *
     * @return The squared length of the vector.
     */
    public double lengthSquared() {
        return this.xyz.d1 * this.xyz.d1 + this.xyz.d2 * this.xyz.d2 + this.xyz.d3 * this.xyz.d3;
    }

    /**
     * Returns the length of this vector.
     *
     * @return The length of the vector.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Calculates the dot product of this vector and another vector.
     *
     * @param v3 The other vector to calculate the dot product with.
     * @return The dot product of this vector and the given vector.
     */
    public double dotProduct(Vector v3) {
        return this.xyz.d1 * v3.xyz.d1 + this.xyz.d2 * v3.xyz.d2 + this.xyz.d3 * v3.xyz.d3;
    }

    @Override
    public Vector add(Vector v1) { return new Vector(this.xyz.add(v1.xyz));}

    /**
     * Multiplies this vector by a scalar and returns the result as a new vector.
     *
     * @param scalar The scalar value to multiply this vector by.
     * @return The result of multiplying the vector by the scalar as a new vector.
     */
    public Vector scale(double scalar) {
        if(isZero(scalar))
            throw new IllegalArgumentException("Vector scale cannot be zero");
        return new Vector(this.xyz.scale(scalar));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Vector && super.equals(obj);
    }

    @Override
    public String toString() {
        return "vector" + xyz;
    }

    /**
     * Calculates the cross product of this vector and another vector.
     *
     * @param v2 The other vector to calculate the cross product with.
     * @return The cross product of this vector and the given vector as a new vector.
     */
    public Vector crossProduct(Vector v2) {
        double x = this.xyz.d2 * v2.xyz.d3 - this.xyz.d3 * v2.xyz.d2;
        double y = this.xyz.d3 * v2.xyz.d1 - this.xyz.d1 * v2.xyz.d3;
        double z = this.xyz.d1 * v2.xyz.d2 - this.xyz.d2 * v2.xyz.d1;
        return new Vector(x, y, z);
    }

    /**
     * Returns a new vector representing the normalized version of this vector.
     *
     * @return A vector representing the normalized vector.
     * @throws IllegalArgumentException If the vector is a zero vector (cannot be normalized).
     */
    public Vector normalize() {
        double length = length();
        // Utilizing the scale function to multiply by scalar and divide by 1
        return scale(1 / length);
    }

}

