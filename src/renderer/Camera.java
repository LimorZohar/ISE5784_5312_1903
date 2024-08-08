package renderer;

import primitives.*;

import java.util.MissingResourceException;
import java.util.stream.IntStream;
import java.util.LinkedList;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Camera class represents a camera in a 3D space with various parameters and methods to construct rays through pixels and render images.
 */
public class Camera implements Cloneable {

    /**
     * The camera's location in 3D space.
     */
    private Point location;

    /**
     * Vector pointing towards the view direction.
     */
    private Vector vTo;

    /**
     * Vector pointing upwards.
     */
    private Vector vUp;

    /**
     * Vector pointing to the right, perpendicular to both vTo and vUp.
     */
    private Vector vRight;

    /**
     * The width of the view plane.
     */
    private double width = 0;

    /**
     * The height of the view plane.
     */
    private double height = 0;

    /**
     * The distance from the camera to the view plane.
     */
    private double distance = 0;

    /**
     * The ImageWriter used to write the image.
     */
    private ImageWriter imageWriter;

    /**
     * The RayTracer used to trace rays and determine pixel colors.
     */
    private RayTracerBase rayTracer;

    /**
     * Number of threads to use for rendering.
     */
    private int threadsCount = 0; // -2 auto, -1 range/stream, 0 no threads, 1+ number of threads

    /**
     * Number of spare threads to keep available.
     */
    private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores

    /**
     * Interval for printing progress percentage.
     */
    private double printInterval = 0; // printing progress percentage interval

    /**
     * PixelManager instance for managing pixels in multi-threading.
     */
    private PixelManager pixelManager; // PixelManager instance

    /**
     * Empty constructor for Camera.
     */
    private Camera() {
    }

    /**
     * Returns a new Builder instance for constructing a Camera.
     *
     * @return Builder instance
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Sets the number of threads to use for rendering.
     *
     * @param threads the number of threads, -2 for auto, -1 for range/stream, 0 for no threads, 1+ for specific number of threads
     * @return the Camera instance
     * @throws IllegalArgumentException if threads is less than -2
     */
    public Camera setMultithreading(int threads) {
        if (threads < -2) throw new IllegalArgumentException("Multithreading must be -2 or higher");
        if (threads >= -1) threadsCount = threads;
        else { // == -2
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            threadsCount = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    /**
     * Calculation of the pixel point in the image plane.
     *
     * @param nX number of horizontal pixels
     * @param nY number of vertical pixels
     * @param j  horizontal index of the pixel
     * @param i  vertical index of the pixel
     * @return Ray from the camera through the pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        // pixels width and height
        double rx = width / nX;
        double ry = height / nY;

        // point[i,j] in view-plane coordinates (center Point)
        Point pij = location.add(vTo.scale(distance));

        // delta values for moving on the view plane
        double xj = (j - (nX - 1) / 2d) * rx;
        double yi = -(i - (nY - 1) / 2d) * ry;

        if (!isZero(xj))
            // add the delta distance to the center of point (i,j)
            pij = pij.add(vRight.scale(xj));
        if (!isZero(yi))
            // add the delta distance to the center of point (i,j)
            pij = pij.add(vUp.scale(yi));

        return new Ray(location, pij.subtract(location));
    }

    /**
     * Renders the image by casting rays through each pixel.
     *
     * @return the Camera instance
     */
    public Camera renderImage() {
        int nx = this.imageWriter.getNx();
        int ny = this.imageWriter.getNy();
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                castRay(i, j);
            }
        }
        return this;
    }

    /**
     * Renders the image by casting rays through each pixel with Anti-Aliasing.
     *
     * @param samplesPerPixel the number of samples per pixel for anti-aliasing
     * @return the Camera instance
     */
    public Camera renderImageWithAntiAliasing(int samplesPerPixel) {
        int nx = this.imageWriter.getNx();
        int ny = this.imageWriter.getNy();
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                castRayWithAntiAliasing(i, j, samplesPerPixel);
            }
        }
        return this;
    }

    /**
     * Renders the image with anti-aliasing by casting multiple rays through each pixel.
     * Supports multi-threading for improved performance.
     *
     * @param samplesPerPixel the number of samples per pixel for anti-aliasing
     * @return the Camera instance
     */
    public Camera renderImageWithAntiAliasingAndThreads(int samplesPerPixel) {
        int nx = this.imageWriter.getNx();
        int ny = this.imageWriter.getNy();

        pixelManager = new PixelManager(ny, nx, printInterval);

        if (threadsCount == 0) {
            for (int i = 0; i < ny; i++) {
                for (int j = 0; j < nx; j++) {
                    castRayWithAntiAliasingAndThreads(i, j, samplesPerPixel);
                }
            }
        } else if (threadsCount == -1) {
            IntStream.range(0, ny).parallel().forEach(i -> {
                IntStream.range(0, nx).parallel().forEach(j -> {
                    castRayWithAntiAliasingAndThreads(i, j, samplesPerPixel);
                });
            });
        } else {
            var threads = new LinkedList<Thread>();
            for (int t = 0; t < threadsCount; t++) {
                threads.add(new Thread(() -> {
                    PixelManager.Pixel pixel;
                    while ((pixel = pixelManager.nextPixel()) != null) {
                        castRayWithAntiAliasingAndThreads(pixel.row(), pixel.col(), samplesPerPixel);
                    }
                }));
            }
            for (var thread : threads) thread.start();
            try {
                for (var thread : threads) thread.join();
            } catch (InterruptedException ignore) {}
        }
        return this;
    }

    /**
     * Prints a grid on the image.
     *
     * @param interval number of height and width of the grid boxes
     * @param color    for the grid
     * @return Camera instance
     */
    public Camera printGrid(int interval, Color color) {
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();

        // running on the view plane
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                // create the net of the grid
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(i, j, color);
                }
            }
        }
        return this;
    }

    /**
     * Starts the process to create the image.
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }

    /**
     * Casts a ray through the specified pixel and colors the pixel based on the ray tracer.
     *
     * @param i the horizontal index of the pixel
     * @param j the vertical index of the pixel
     */
    private void castRay(int i, int j) {
        Ray ray = constructRay(imageWriter.getNx(), imageWriter.getNy(), j, i);
        imageWriter.writePixel(j, i, rayTracer.traceRay(ray));
    }

    /**
     * Casts multiple rays through each pixel for anti-aliasing and colors the pixel based on the average color.
     *
     * @param i               the horizontal index of the pixel
     * @param j               the vertical index of the pixel
     * @param samplesPerPixel the number of samples per pixel
     */
    private void castRayWithAntiAliasing(int i, int j, int samplesPerPixel) {
        Color averageColor = Color.BLACK;
        for (int s = 0; s < samplesPerPixel; s++) {
            double rx = width / imageWriter.getNx();
            double ry = height / imageWriter.getNy();
            double xj = (j - (imageWriter.getNx() - 1) / 2.0 + randomInUnitInterval()) * rx;
            double yi = -(i - (imageWriter.getNy() - 1) / 2.0 + randomInUnitInterval()) * ry;

            Point pij = location.add(vTo.scale(distance));
            if (!isZero(xj)) pij = pij.add(vRight.scale(xj));
            if (!isZero(yi)) pij = pij.add(vUp.scale(yi));

            Ray ray = new Ray(location, pij.subtract(location));
            averageColor = averageColor.add(rayTracer.traceRay(ray));
        }
        averageColor = averageColor.reduce(samplesPerPixel);
        imageWriter.writePixel(j, i, averageColor);
    }

    /**
     * Casts multiple rays through each pixel for anti-aliasing and colors the pixel based on the average color.
     * This function supports multi-threading.
     *
     * @param i               the horizontal index of the pixel
     * @param j               the vertical index of the pixel
     * @param samplesPerPixel the number of samples per pixel
     */
    private void castRayWithAntiAliasingAndThreads(int i, int j, int samplesPerPixel) {
        Color averageColor = Color.BLACK;
        for (int s = 0; s < samplesPerPixel; s++) {
            double rx = width / imageWriter.getNx();
            double ry = height / imageWriter.getNy();
            double xj = (j - (imageWriter.getNx() - 1) / 2.0 + randomInUnitInterval()) * rx;
            double yi = -(i - (imageWriter.getNy() - 1) / 2.0 + randomInUnitInterval()) * ry;

            Point pij = location.add(vTo.scale(distance));
            if (!isZero(xj)) pij = pij.add(vRight.scale(xj));
            if (!isZero(yi)) pij = pij.add(vUp.scale(yi));

            Ray ray = new Ray(location, pij.subtract(location));
            averageColor = averageColor.add(rayTracer.traceRay(ray));
        }
        averageColor = averageColor.reduce(samplesPerPixel);
        imageWriter.writePixel(j, i, averageColor);
        pixelManager.pixelDone();
    }

    /**
     * Returns a random number in the interval [0, 1).
     *
     * @return random double in [0, 1)
     */
    private double randomInUnitInterval() {
        return Math.random();
    }

    /**
     * Builder class for constructing a Camera instance.
     */
    public static class Builder {

        /**
         * Camera.
         */
        private final Camera camera;

        /**
         * Builder empty constructor.
         * Acts as an empty constructor for Camera.
         */
        public Builder() {
            this.camera = new Camera();
        }

        /**
         * Builder constructor with parameters.
         * Acts as a copy constructor for Camera.
         *
         * @param camera Camera instance to copy
         */
        @SuppressWarnings("unused")
        public Builder(Camera camera) {
            this.camera = camera;
        }

        /**
         * Sets the location of the camera.
         *
         * @param location Point representing the camera's location
         * @return Builder instance
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
         * @param vTo Vector pointing to the view direction
         * @param vUp Vector pointing to the up direction
         * @return Builder instance
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (vTo == null || vUp == null) {
                throw new IllegalArgumentException("Vectors cannot be null");
            }
            if (!isZero(vTo.dotProduct(vUp))) {
                throw new IllegalArgumentException("vTo and vUp must be orthogonal");
            }
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            return this;
        }

        /**
         * Sets the size of the view plane.
         *
         * @param width  width of the view plane
         * @param height height of the view plane
         * @return Builder instance
         */
        public Builder setVpSize(double width, double height) {
            if (isZero(width) || isZero(height)) {
                throw new IllegalArgumentException("Width and height must be greater than 0");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Sets the distance of the camera from the view plane.
         *
         * @param vpDistance distance of the camera from the view plane
         * @return Builder instance
         */
        public Builder setVpDistance(double vpDistance) {
            if (isZero(alignZero(vpDistance))) {
                throw new IllegalArgumentException("Distance must be greater than 0");
            }
            camera.distance = vpDistance;
            return this;
        }

        /**
         * Returns the constructed Camera instance after validating the parameters.
         *
         * @return Camera instance
         */
        public Camera build() {
            if (camera.location == null) {
                throw new MissingResourceException("Missing rendering data", "Camera", "location");
            }
            if (camera.vTo == null || camera.vUp == null || camera.vRight == null) {
                throw new MissingResourceException("Missing rendering data", "Camera", "direction vectors");
            }
            if (isZero(camera.width)) {
                throw new MissingResourceException("Missing rendering data", "Camera", "viewport width");
            }
            if (isZero(camera.height)) {
                throw new MissingResourceException("Missing rendering data", "Camera", "viewport height");
            }
            if (isZero(camera.distance)) {
                throw new MissingResourceException("Missing rendering data", "Camera", "viewport distance");
            }
            if (camera.imageWriter == null) {
                throw new MissingResourceException("Missing rendering data",
                        "Camera",
                        "imageWriter");
            }
            if (camera.rayTracer == null) {
                throw new MissingResourceException("Missing rendering data",
                        "Camera",
                        "rayTracer");
            }

            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException ignore) {
                throw new AssertionError();
            }
        }

        /**
         * Sets the ImageWriter for the camera.
         *
         * @param imageWriter ImageWriter instance
         * @return Builder instance
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            if (imageWriter == null)
                throw new IllegalArgumentException("ImageWriter cannot be null");
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the RayTracer for the camera.
         *
         * @param rayTracer RayTracerBase instance
         * @return Builder instance
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
