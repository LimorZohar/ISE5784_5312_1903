package renderer;

import primitives.*;

import java.util.MissingResourceException;

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
    private Vector vUp;

    /**
     * The right direction vector of the camera.
     */
    private Vector vRight;

    /**
     * The forward direction vector of the camera.
     */
    private Vector vTo;

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
     * Gets the location of the camera.
     *
     * @return the location of the camera
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Gets the up direction vector of the camera.
     *
     * @return the up direction vector of the camera
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * Gets the right direction vector of the camera.
     *
     * @return the right direction vector of the camera
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * Gets the forward direction vector of the camera.
     *
     * @return the forward direction vector of the camera
     */
    public Vector getvTo() {
        return vTo;
    }

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
     * Builder class for constructing a Camera object.
     */
    public static class Builder {
        private final Camera camera = new Camera();

        /**
         * Sets the location of the camera.
         *
         * @param location the location of the camera
         * @return the builder instance
         */
        public Builder setLocation(Point location) {
            camera.location = location;
            return this;
        }

        /**
         * Sets the direction of the camera.
         *
         * @param vTo the direction of the camera (the vector from the camera to the "look-at" point)
         * @param vUp the up direction of the camera (the vector from the camera to the up direction)
         * @return the builder instance
         * @throws IllegalArgumentException if vTo and vUp are not orthogonal
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (!isZero(vTo.dotProduct(vUp))) {
                throw new IllegalArgumentException("vTo and vUp are not orthogonal");
            }
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            return this;
        }

        /**
         * Sets the size of the view plane.
         *
         * @param width  the width of the view plane
         * @param height the height of the view plane
         * @return the builder instance
         * @throws IllegalArgumentException if width or height are not positive
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("width and height are not positive");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Sets the distance between the camera and the view plane.
         *
         * @param distance the distance between the camera and the view plane
         * @return the builder instance
         * @throws IllegalArgumentException if distance is not positive
         */
        public Builder setVPDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("distance from camera to view must be positive");
            }
            camera.distance = distance;
            return this;
        }

        /**
         * Builds the Camera object.
         *
         * @return the built Camera object
         * @throws MissingResourceException if any required field is not set
         * @throws IllegalArgumentException if the direction vectors are not orthogonal or not normalized
         */
        public Camera build() {
            String className = "Camera";
            String description = "values not set";

            if (camera.location == null)
                throw new MissingResourceException(className, description, "location");
            if (camera.vUp == null)
                throw new MissingResourceException(className, description, "vUp");
            if (camera.vTo == null)
                throw new MissingResourceException(className, description, "vTo");
            if (camera.width == 0)
                throw new MissingResourceException(className, description, "width");
            if (camera.height == 0)
                throw new MissingResourceException(className, description, "height");
            if (camera.distance == 0)
                throw new MissingResourceException(className, description, "distance");

            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

            if (!isZero(camera.vTo.dotProduct(camera.vRight)) ||
                    !isZero(camera.vTo.dotProduct(camera.vUp)) ||
                    !isZero(camera.vRight.dotProduct(camera.vUp)))
                throw new IllegalArgumentException("vTo, vUp and vRight must be orthogonal");

            if (camera.vTo.length() != 1 || camera.vUp.length() != 1 || camera.vRight.length() != 1)
                throw new IllegalArgumentException("vTo, vUp and vRight must be normalized");

            if (camera.width <= 0 || camera.height <= 0)
                throw new IllegalArgumentException("width and height must be positive");

            if (camera.distance <= 0)
                throw new IllegalArgumentException("distance from camera to view must be positive");

            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Default constructor for Camera.
     */
    Camera() {
    }

    /**
     * Gets a new builder instance for Camera.
     *
     * @return the camera builder
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Constructs a ray through a given pixel in the view plane.
     *
     * @param nX the number of pixels in the x direction
     * @param nY the number of pixels in the y direction
     * @param j  the x index of the pixel
     * @param i  the y index of the pixel
     * @return the ray that passes through the pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        // View plane center point
        Point pc = location.add(vTo.scale(distance));

        // Pixel ratios (pixel width and height)
        double rx = width / nX;
        double ry = height / nY;

        // Point[i,j] in view-plane coordinates
        Point pij = pc;

        // Delta values for moving on the view plane
        double xj = (j - (nX - 1) / 2d) * rx;
        double yi = -(i - (nY - 1) / 2d) * ry;

        // If not on zero coordinates, add the delta distance to the center of point (i,j) to reach it
        if (!isZero(xj)) {
            pij = pij.add(vRight.scale(xj));
        }
        if (!isZero(yi)) {
            pij = pij.add(vUp.scale(yi));
        }

        // Return location and vector from camera's eye in the direction of point (i,j) in the view plane
        return new Ray(location, pij.subtract(location));
    }
}
