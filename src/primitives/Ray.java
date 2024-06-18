package primitives;

import java.util.List;

/**
 * Represents a ray in three-dimensional space defined by a head point and a direction vector.
 */
public class Ray {
    /**
     * The head point of the ray.
     */
    final private Point head;
    /**
     * The direction vector of the ray (must have a length of 1 for a normal direction).
     */
    final private Vector direction;

    /**
     * Constructs a new Ray with the specified head point and direction vector.
     *
     * @param head      The head point of the ray.
     * @param direction The direction vector of the ray.
     * @throws IllegalArgumentException If the direction vector does not have a length of 1 (not normal).
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    /**
     * Returns the head point of the ray.
     *
     * @return The head point of the ray.
     */
    public Point getHead() {
        return head;
    }

    /**
     * Returns the direction vector of the ray.
     *
     * @return The direction vector of the ray.
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * Gets a point on the ray
     * by calculating head + t*direction.
     *
     * @param t A scalar to calculate the point.
     * @return A point on the ray.
     */
    public Point getPoint(double t) {
        return Util.isZero(t) ? head : head.add(direction.scale(t));
    }

    /**
     * Finds the closest point from a list of points to the head of the ray.
     *
     * @param pointList A list of points to search from.
     * @return The closest point to the head of the ray, or null if the list is empty.
     */
    public Point findClosestPoint(List<Point> pointList) {
        return null;
    }

    /**
     * Compares this ray to the specified object. The result is true if and only if the argument is not null
     * and is a Ray object that has the same direction vector and head point as this ray.
     *
     * @param obj The object to compare this ray against.
     * @return true if the given object represents a Ray equivalent to this ray, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Ray other && this.direction.equals(other.direction) && this.head.equals(other.head);
    }

    /**
     * Returns a string representation of this ray. The string representation consists of the direction vector
     * followed by an arrow ("->") and the head point.
     *
     * @return A string representation of this ray.
     */
    @Override
    public String toString() {
        return this.direction + "->" + this.head;
    }


}
