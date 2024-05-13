package primitives;

/**
 * Represents a ray in three-dimensional space defined by a head point and a direction vector.
 */
public class Ray {
    /** The head point of the ray. */
    final Point head;
    /** The direction vector of the ray (must have a length of 1 for a normal direction). */
    final Vector directions;

    /**
     * Constructs a new Ray with the specified head point and direction vector.
     * @param head The head point of the ray.
     * @param directions The direction vector of the ray.
     * @throws IllegalArgumentException If the direction vector does not have a length of 1 (not normal).
     */
    public Ray(Point head, Vector directions) {
        this.head = head;
        this.directions = (Vector) directions.normalize();

    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Ray)) return false;
        return (this.equals(this.directions) && this.head.equals(((Ray) obj).head));
    }


    @Override
    public String toString() {
        return (this.directions.toString() + " " + this.head.toString());
    }
}
