package primitives;

/**
 * Represents a ray in three-dimensional space defined by a head point and a direction vector.
 */
public class Ray {
    /**
     * The head point of the ray.
     */
    final protected Point head;
    /**
     * The direction vector of the ray (must have a length of 1 for a normal direction).
     */
    final protected Vector direction;

    /**
     * Constructs a new Ray with the specified head point and direction vector.
     *
     * @param head       The head point of the ray.
     * @param direction The direction vector of the ray.
     * @throws IllegalArgumentException If the direction vector does not have a length of 1 (not normal).
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Ray other && this.direction.equals(other.direction) && this.head.equals(other.head);
    }

    @Override
    public String toString() {
        return this.direction + "->" + this.head;
    }
}
