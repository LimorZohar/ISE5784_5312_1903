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
     * Camera getter
     * @return the location of the camera
     */
    public Point getLocation()
    {
        return location;
    }

    /**
     * Camera getter
     * @return the up direction of the camera
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * Camera getter
     * @return the right direction of the camera
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * Camera getter
     * @return the direction of the camera
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
     * Camera builder
     */
    public static class Builder {
        private final Camera camera = new Camera();

        /**
         * Set the location of the camera
         *
         * @param location the location of the camera
         */
        public Builder setLocation(Point location) {
            camera.location = location;
            return this;
        }

        /**
         * Set the direction of the camera
         *
         * @param vTo the direction of the camera
         *            (the vector from the camera to the "look-at" point)
         * @param vUp the up direction of the camera
         *                     (the vector from the camera to the up direction)
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (!Util.isZero(vTo.dotProduct(vUp))) {
                throw new IllegalArgumentException("vTo and vUp must be orthogonal");
            }
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            return this;
        }

        /**
         * Set the size of the view plane
         *
         * @param width  the width of the view plane
         * @param height the height of the view plane
         */
        public Builder setVpSize(double width, double height) {
            if(width <= 0 || height <= 0) {
                throw new IllegalArgumentException("width and height must be positive");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Set the distance between the camera and the view plane
         *
         * @param distance the distance between the camera and the view plane
         */
        public Builder setVPDistance(double distance) {
            if(distance <= 0) {
                throw new IllegalArgumentException("distance from camera to view must be positive");
            }
            camera.distance = distance;
            return this;
        }

        /**
         * Build the camera
         *
         * @return the camera
         */
        public Camera build() {
            String className = "Camera";
            String description = "values not set";

            if(camera.location == null)
                throw new MissingResourceException(className, description, "p0");
            if(camera.vUp == null)
                throw new MissingResourceException(className, description, "vUp");
            if(camera.vTo == null)
                throw new MissingResourceException(className, description, "vTo");
            if(camera.width == 0d)
                throw new MissingResourceException(className, description, "width");
            if(camera.height == 0d)
                throw new MissingResourceException(className, description, "height");
            if(camera.distance == 0d)
                throw new MissingResourceException(className, description, "distance");

            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

            if(!Util.isZero(camera.vTo.dotProduct(camera.vRight)) ||
                    !Util.isZero(camera.vTo.dotProduct(camera.vUp)) ||
                    !Util.isZero(camera.vRight.dotProduct(camera.vUp)))
                throw new IllegalArgumentException("vTo, vUp and vRight must be orthogonal");

            if(camera.vTo.length() != 1 || camera.vUp.length() != 1 || camera.vRight.length() != 1)
                throw new IllegalArgumentException("vTo, vUp and vRight must be normalized");

            if(camera.width <= 0 || camera.height <= 0)
                throw new IllegalArgumentException("width and height must be positive");

            if(camera.distance <= 0)
                throw new IllegalArgumentException("distance from camera to view must be positive");

            try {
                return  (Camera)camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Camera constructor
     */
    Camera() {}

    /**
     * Builder getter
     *
     * @return the camera builder
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * construct a ray through a pixel
     *
     * @param nX the number of pixels in the x direction
     * @param nY the number of pixels in the y direction
     * @param j  the x index of the pixel
     * @param i  the y index of the pixel
     * @return the ray that passes through the pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        //view plane center Point
        Point pc = location.add(vTo.scale(distance));

        //pixels ratios (pixels width and height)
        double rx = width / nX;
        double ry = height / nY;

        //Pij point[i,j] in view-plane coordinates
        Point pij = pc;

        //delta values for moving on the view plane
        double xj = (j - (nX - 1) / 2d) * rx;
        double yi = -(i - (nY - 1) / 2d) * ry;

        //if not on zero coordinates add the delta distance
        // to the center of point (i,j)
        // to reach it
        if (!isZero(xj)) {
            pij = pij.add(vRight.scale(xj));
        }
        if (!isZero(yi)) {
            pij = pij.add(vUp.scale(yi));
        }

        // return location and vector from camera's eye in the direction of point(i,j) in the view plane
        return new Ray(location, pij.subtract(location));
    }

}

