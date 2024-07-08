package primitives;

import static primitives.Util.isZero;

/**
 * Represents a vector in three-dimensional space, extending the Point class.
 */
public class Vector extends Point {

    /**
     * Constant vector pointing in the Y direction (0, 1, 0).
     */
    public static final Vector Y = new Vector(0, 1, 0); // הוספת הקבוע Y

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

    /**
     * Adds this vector to another vector and returns the result as a new vector.
     *
     * @param v1 The vector to add to this vector.
     * @return The result of the addition as a new vector.
     */
    public Vector add(Vector v1) {
        return new Vector(this.xyz.add(v1.xyz));
    }

    /**
     * Multiplies this vector by a scalar and returns the result as a new vector.
     *
     * @param scalar The scalar value to multiply this vector by.
     * @return The result of multiplying the vector by the scalar as a new vector.
     * @throws IllegalArgumentException If scaling by zero or a very small number.
     */
    public Vector scale(double scalar) {
        return new Vector(this.xyz.scale(scalar));
    }

    /**
     * Calculates the cross product of this vector and another vector.
     *
     * @param v2 The other vector to calculate the cross product with.
     * @return The cross product of this vector and the given vector as a new vector.
     * @throws IllegalArgumentException If the vectors are parallel.
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
     */
    public Vector normalize() {
        // Utilizing the scale function to multiply by scalar and divide by 1
        return scale(1 / length());
    }

    /**
     * Compares this vector to the specified object. The result is true if and only if the argument is not null
     * and is a Vector object that represents the same vector as this object.
     *
     * @param obj The object to compare this vector against.
     * @return true if the given object represents a Vector equivalent to this vector, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Vector && super.equals(obj);
    }

    /**
     * Returns a string representation of this vector.
     *
     * @return A string representation of this vector.
     */
    @Override
    public String toString() {
        return "vector" + xyz;
    }
}
