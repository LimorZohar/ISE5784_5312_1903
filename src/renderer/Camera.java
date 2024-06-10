package renderer;

import primitives.*;
import static primitives.Util.*;

/**
 * The Camera class represents a camera in a 3D space.
 * It holds information about the camera's position, orientation, and view plane dimensions.
 */
public class Camera implements Cloneable {
    /**
     * The location of the camera in 3D space.
     */
    private Point location;

    /**
     * The up direction vector of the camera.
     */
    private Vector v_up;

    /**
     * The right direction vector of the camera.
     */
    private Vector v_right;

    /**
     * The forward direction vector of the camera.
     */
    private Vector v_to;

    /**
     * The height of the view plane.
     */
    private double height = 0.0;

    /**
     * The width of the view plane.
     */
    private double width = 0.0;

    /**
     * The distance from the camera to the view plane.
     */
    private double distance = 0.0;

    /**
     * Gets the height of the view plane.
     *
     * @return the height of the view plane
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets the width of the view plane.
     *
     * @return the width of the view plane
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the distance from the camera to the view plane.
     *
     * @return the distance from the camera to the view plane
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Default constructor for the Camera class.
     */
    private Camera() {
    }

    /**
     * Returns a new Builder for the Camera class.
     *
     * @return a new Builder instance for the Camera class
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Constructs a ray through a specific pixel in the view plane.
     *
     * @param nX number of pixels in the X direction
     * @param nY number of pixels in the Y direction
     * @param j  pixel index in the X direction
     * @param i  pixel index in the Y direction
     * @return the constructed Ray through the specified pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        // Implement the method to construct the ray
        return null;
    }

    /**
     * Builder class for constructing Camera instances.
     */
    public static class Builder {
        // Builder instance of Camera
        private final Camera camera = new Camera();

        /**
         * Sets the location of the camera.
         *
         * @param location the location to set
         * @return the Builder instance
         * @throws IllegalArgumentException if the location is null
         */
        public Builder setLocation(Point location) {
            if (location == null) {
                throw new IllegalArgumentException("Location cannot be null");
            }
            camera.location = location;
            return this;
        }

        /**
         * Sets the direction of the camera.
         *
         * @param vTo the forward direction vector
         * @param vUp the up direction vector
         * @return the Builder instance
         * @throws IllegalArgumentException if the vectors are null or not orthogonal
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (vTo == null || vUp == null) {
                throw new IllegalArgumentException("Direction vectors cannot be null");
            }
            if (!isZero(vTo.dotProduct(vUp))) {
                throw new IllegalArgumentException("Direction vectors must be orthogonal");
            }
            camera.v_to = vTo.normalize();
            camera.v_up = vUp.normalize();
            camera.v_right = vTo.crossProduct(vUp).normalize();
            return this;
        }

        /**
         * Sets the size of the view plane.
         *
         * @param width  the width to set
         * @param height the height to set
         * @return the Builder instance
         * @throws IllegalArgumentException if width or height are non-positive
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Width and height must be positive");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Sets the distance from the camera to the view plane.
         *
         * @param distance the distance to set
         * @return the Builder instance
         * @throws IllegalArgumentException if distance is non-positive
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("Distance must be positive");
            }
            camera.distance = distance;
            return this;
        }

        /**
         * Builds and returns the Camera instance.
         *
         * @return the constructed Camera instance
         */
        public Camera build() {
            return camera;
        }
    }
}
