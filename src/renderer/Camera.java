package renderer;

import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.*;

/**
 * Camera class
 */
public class Camera implements Cloneable {
    private Point p0;
    private Vector vTo, vUp, vRight;
    private double width = 0, height = 0, distance = 0;

    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    /**
     * empty constructor
     */
    private Camera() { }

    /**
     * returns a new Builder
     * @return Builder
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Calculation of the pixel point in the image plane
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        //pixels width and height
        double rx = width / nX;
        double ry = height / nY;

        //point[i,j] in view-plane coordinates (center Point)
        Point pij = p0.add(vTo.scale(distance));

        //delta values for moving on the view plane
        double xj = (j - (nX - 1) / 2d) * rx;
        double yi = -(i - (nY - 1) / 2d) * ry;

        if (!isZero(xj)) {
            //add the delta distance to the center of point (i,j)
            pij = pij.add(vRight.scale(xj));
        }
        if (!isZero(yi)) {
            //add the delta distance to the center of point (i,j)
            pij = pij.add(vUp.scale(yi));
        }
        Vector Vij = pij.subtract(p0); // vector from camera's place

        return new Ray(p0, Vij);
    }

    /**
     * Casts a ray for each pixel
     * @return a camera
     */
    public Camera renderImage() {
        if (this.imageWriter == null)
            throw new UnsupportedOperationException("Missing imageWriter");
        if (this.rayTracer == null)
            throw new UnsupportedOperationException("Missing rayTracerBase");

        for (int i = 0; i < this.imageWriter.getNx(); i++) {
            for (int j = 0; j < this.imageWriter.getNy(); j++) {
                castRay(i,j);
            }
        }
        return this;
    }

    /**
     * this function prints a grid net on the image
     * @param interval number of height and width of the grid boxes
     * @param color for the grid
     * @return camera
     */
    public Camera printGrid(int interval, Color color) {
        //running on the view plane
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                //create the net of the grid
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(i, j, color);
                }
            }
        }
        return this;
    }
    /**
     * This function starts the method to create the image
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }

    /**
     * this function colors the j pixels
     * @param i  resolution
     * @param j number of pixels
     */
    private void castRay(int i,int j){
        Ray ray = constructRay(this.imageWriter.getNx(), this.imageWriter.getNy(), j, i);
        Color color = rayTracer.traceRay(ray);
        imageWriter.writePixel(j, i, color);
    }

    /**
     * Builder class in camera class
     */
    public static class Builder {
        private final Camera camera;

        /**
         * Builder empty constructor
         * acts like empty constructor for camera
         */
        public Builder() {
            this.camera = new Camera();
        }

        /**
         * Builder constructor with parameters
         * acts like copy constructor for camera
         * @param camera
         */
        public Builder(Camera camera) {
            this.camera = camera;
        }

        //setters:
        /**
         * set Location
         * @param location
         * @return Builder
         */
        public Builder setLocation(Point location) {
            if (location == null) {
                throw new IllegalArgumentException("Location cannot be null");
            }
            camera.p0 = location;
            return this;
        }

        /**
         * set Direction
         * @param vTo
         * @param vUp
         * @return Builder
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (vTo == null || vUp == null) {
                throw new IllegalArgumentException("Vectors cannot be null");
            }
            if (vTo.dotProduct(vUp) != 0) {
                throw new IllegalArgumentException("vTo and vUp must be orthogonal");
            }
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            return this;
        }

        /**
         * set VpSize => sets height and width
         * @param width
         * @param height
         * @return
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Width and height must be greater than 0");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * set VpDistance => distance of camera
         * @param vpDistance
         * @return
         */
        public Builder setVpDistance(double vpDistance) {
            if (vpDistance <= 0) {
                throw new IllegalArgumentException("Distance must be greater than 0");
            }
            camera.distance = vpDistance;
            return this;
        }

        /**
         * return the camera
         * after the function checks the parameters in the camera
         * @return Camera
         */
        public Camera build() {
            if (camera.p0 == null) {
                throw new MissingResourceException("Missing rendering data", "Camera", "location");
            }
            if (camera.vTo == null || camera.vUp == null || camera.vRight == null) {
                throw new MissingResourceException("Missing rendering data", "Camera", "direction vectors");
            }
            if (camera.width == 0 || camera.height == 0) {
                throw new MissingResourceException("Missing rendering data", "Camera", "viewport size");
            }
            if (camera.distance == 0) {
                throw new MissingResourceException("Missing rendering data", "Camera", "viewport distance");
            }
            try{
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw null;
            }
        }

        public Builder setImageWriter(ImageWriter imageWriter) {
            if (imageWriter == null) {
                throw new IllegalArgumentException("Distance must be greater than 0");
            }
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * set RayTracer
         * @param rayTracer to know the color in a point
         * @return this
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            if (rayTracer == null) {
                throw new IllegalArgumentException("rayTracer cannot be null");
            }
            camera.rayTracer = rayTracer;
            return this;
        }
    }
}
