package renderer;

import geometries.Cylinder;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import lighting.PointLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;
import lighting.DirectionalLight;
import lighting.SpotLight;
import lighting.AmbientLight;

/**
 * Class for creating a sunset scene with trees and clouds.
 */
public class SunsetScene {

    /**
     * Color of the sun in the scene.
     */
    private static final Color SUN_COLOR = new Color(255, 69, 0);

    /**
     * Color of the clouds in the scene.
     */
    private static final Color CLOUD_COLOR = new Color(400, 100, 50);

    /**
     * Color of the tree leaves in the scene.
     */
    private static final Color TREE_GREEN = new Color(50, 60, 10);

    /**
     * Color of the tree trunks in the scene.
     */
    private static final Color TREE_BROWN = new Color(92, 47, 5);

    /**
     * The scene containing the sunset, trees, and clouds.
     */
    private final Scene scene = new Scene("Combined Sunset and Trees Scene");

    /**
     * Builder for the camera to capture the scene.
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setRayTracer(new SimpleRayTracer(scene));

    /**
     * Creates the sunset part of the scene.
     *
     * @param x    The x-coordinate of the center of the sunset.
     * @param y    The y-coordinate of the center of the sunset.
     * @param z    The z-coordinate of the center of the sunset.
     * @param size The size of the sunset.
     * @return Geometries representing the sunset.
     */
    private Geometries createSunset(double x, double y, double z, double size) {
        return new Geometries(
                // Sun (as a sphere)
                new Sphere(new Point(x, y, z), size / 3.5)
                        .setEmission(SUN_COLOR)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),

                // Sky
                new Triangle(new Point(x - size * 4, y + size + 150, z - 10),
                        new Point(x + size * 4, y + size + 150, z - 10), new Point(x, y, z - 10))
                        .setEmission(new Color(300, 100, 50)) // yellow front
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(200)),
                new Triangle(new Point(x - size * 3, y + size, z - 20),
                        new Point(x + size * 3, y + size, z - 20), new Point(x, y, z - 20))
                        .setEmission(new Color(400, 80, 50)) // orange
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),
                new Triangle(new Point(x - size * 3, y + size - 20, z - 30),
                        new Point(x + size * 3, y + size - 20, z - 30), new Point(x, y - 15, z - 30))
                        .setEmission(new Color(600, 60, 50)) // red
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100))
        );
    }

    /**
     * Creates a cloud in the scene.
     *
     * @param x    The x-coordinate of the center of the cloud.
     * @param y    The y-coordinate of the center of the cloud.
     * @param z    The z-coordinate of the center of the cloud.
     * @param size The size of the cloud.
     * @return Geometries representing the cloud.
     */
    private Geometries createCloud(double x, double y, double z, double size) {
        return new Geometries(
                new Sphere(new Point(x, y, z), size / 2)
                        .setEmission(CLOUD_COLOR)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKt(0.3)),
                new Sphere(new Point(x - size / 2.5, y, z), size / 2.5)
                        .setEmission(CLOUD_COLOR)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKt(0.3)),
                new Sphere(new Point(x + size / 2.5, y, z), size / 2.5)
                        .setEmission(CLOUD_COLOR)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKt(0.3)),
                new Sphere(new Point(x - size / 4, y - size / 4, z), size / 3)
                        .setEmission(CLOUD_COLOR)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKt(0.3)),
                new Sphere(new Point(x + size / 4, y - size / 4, z), size / 3)
                        .setEmission(CLOUD_COLOR)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKt(0.3))
        );
    }

    /**
     * Creates a tree in the scene.
     *
     * @param x      The x-coordinate of the base of the tree.
     * @param y      The y-coordinate of the base of the tree.
     * @param z      The z-coordinate of the base of the tree.
     * @param height The height of the tree.
     * @return Geometries representing the tree.
     */
    private Geometries createTree(double x, double y, double z, double height) {
        return new Geometries(
                new Triangle(new Point(x - 10, y - (height * 0.3), z),
                        new Point(x + 12, y - (height * 0.3), z), new Point(x, y, z))
                        .setEmission(TREE_GREEN)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKt(0).setKr(0)),
                new Triangle(new Point(x - 20, y - (height * 0.6), z - 10),
                        new Point(x + 20, y - (height * 0.6), z - 10), new Point(x + 3, y - 5, z - 10))
                        .setEmission(TREE_GREEN)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKt(0).setKr(0)),
                new Triangle(new Point(x - 25, y - (height * 0.85), z - 20),
                        new Point(x + 25, y - (height * 0.85), z - 20), new Point(x + 5, y - 10, z - 20))
                        .setEmission(TREE_GREEN)
                        .setMaterial(new Material().setKd(0.6).setKs(0.1).setShininess(100).setKt(0).setKr(0)),
                new Cylinder(4, new Ray(new Point(x, y - height - 5, z - 30),
                        new Vector(0, 1, 0)), (height * 0.3))
                        .setEmission(TREE_BROWN)
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(10).setKt(0).setKr(0))
        );
    }

    /**
     * Creates additional hidden objects in the scene.
     *
     * @param x    The x-coordinate of the center of the objects.
     * @param y    The y-coordinate of the center of the objects.
     * @param z    The z-coordinate of the center of the objects.
     * @param size The size of the objects.
     * @return Geometries representing the additional objects.
     */
    private Geometries createHiddenObjects(double x, double y, double z, double size) {
        return new Geometries(
                // Hidden sphere under the ground
                new Sphere(new Point(x, y - size, z - 200), size / 4)
                        .setEmission(new Color(92, 47, 5))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(10).setKt(0).setKr(0)),
                // Hidden cylinder behind the trees
                new Cylinder(10, new Ray(new Point(x + 200, y - 50, z ), new Vector(-1, 0, 1)), 50)
                        .setEmission(new Color(92, 47, 5))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(10).setKt(0).setKr(0)),
                // Hidden triangle under the clouds
                new Triangle(new Point(x - 100, y + 100, z - 150),
                        new Point(x + 100, y + 100, z - 150),
                        new Point(x, y + 50, z - 200))
                        .setEmission(new Color(92, 47, 5))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(10).setKt(0).setKr(0)),
                new Sphere(new Point(x, y - size, z - 200), size / 4)
                        .setEmission(new Color(92, 47, 5))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(10).setKt(0).setKr(0)),
                // Hidden cylinder behind the trees
                new Cylinder(10, new Ray(new Point(x + 200, y - 50, z ), new Vector(-1, 0, 1)), 50)
                        .setEmission(new Color(92, 47, 5))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(10).setKt(0).setKr(0)),
                // Hidden triangle under the clouds
                new Triangle(new Point(x - 100, y + 100, z - 150),
                        new Point(x + 100, y + 100, z - 150),
                        new Point(x, y + 50, z - 200))
                        .setEmission(new Color(92, 47, 5))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(10).setKt(0).setKr(0)),
                new Sphere(new Point(x, y - size, z - 200), size / 4)
                        .setEmission(new Color(92, 47, 5))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(10).setKt(0).setKr(0)),
                // Hidden cylinder behind the trees
                new Cylinder(10, new Ray(new Point(x + 200, y - 50, z ), new Vector(-1, 0, 1)), 50)
                        .setEmission(new Color(92, 47, 5))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(10).setKt(0).setKr(0)),
                // Hidden triangle under the clouds
                new Triangle(new Point(x - 100, y + 100, z - 150),
                        new Point(x + 100, y + 100, z - 150),
                        new Point(x, y + 50, z - 200))
                        .setEmission(new Color(92, 47, 5))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(10).setKt(0).setKr(0))
        );
    }

    /**
     * Creates the combined sunset and trees scene and renders it.
     *
     * @param withAA      Whether to render with Anti-Aliasing.
     * @param withThreads Whether to render with Multi-Threading.
     */
    public void createSunsetPic(boolean withAA, boolean withThreads) {
        scene.setBackground(new Color(50, 119, 153));

        // Add the sunset to the scene (back layer)
        scene.geometries.add(createSunset(0, 0, -1500, 175));

        // Add more triangles to the sky
        double size = 170;
        double x = 0;
        double y = 0;
        double z = -1500;
        double tree_size = 60;
        // Add trees to the scene (middle-back layer)
        scene.geometries.add(createTree(-150, -28, -900, tree_size));
        scene.geometries.add(createTree(-100, -21, -900, tree_size));
        scene.geometries.add(createTree(-50, -13, -900, tree_size));
        scene.geometries.add(createTree(50, -17, -900, tree_size));
        scene.geometries.add(createTree(100, -21, -900, tree_size));
        scene.geometries.add(createTree(150, -31, -900, tree_size));

        // Add clouds to the scene (middle layer)
        scene.geometries.add(createCloud(-30, 50, -250, 40));
        scene.geometries.add(createCloud(50, 70, -100, 35));
        scene.geometries.add(createCloud(0, 40, -50, 20));

        // Add more clouds to the scene (front layer)
        scene.geometries.add(createCloud(-80, 30, -50, 20));
        scene.geometries.add(createCloud(80, 60, -50, 25));
        scene.geometries.add(createCloud(20, 20, -50, 15));

        // Add reflection of the sunset
        scene.geometries.add(
                new Triangle(new Point(x - size * 4, y - size - 10, -800),
                        new Point(x + size * 4, y - size - 10, -800),
                        new Point(x, y - 68, -900))
                        .setEmission(new Color(500, 100, 50))
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKt(0.9).setKr(1))
        );

        scene.geometries.add(createHiddenObjects(0, 0, 900, 175));

        // Add lights to the scene
        scene.lights.add(new DirectionalLight(new Color(75, 75, 75), new Vector(1, -1, -1)));
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.yellow), 0.01));

        // Add additional light sources
        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(0, 10, -950),
                new Vector(0, -15, 2))
                .setkC(1).setkL(0.0001).setkQ(0.000005));
        scene.lights.add(new SpotLight(new Color(500, 300, 300), new Point(300, 300, 300),
                new Vector(-1, -1, -2))
                .setkC(1).setkL(0.0001).setkQ(0.00001));

        // Set up the camera and render the image
        var camera = cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("SunsetPicture" + (withAA ? "AA" : "") + (withThreads ? "MT" : ""), 500, 500))
                .build();

        if (withThreads) {
            camera.setMultithreading(4); // שימוש ב-4 תהליכונים
            camera.renderImageWithAntiAliasingAndThreads(10); //antialiasing
        } else if (withAA) {
            camera.renderImageWithAntiAliasing(10); //antialiasing
        } else {
            camera.renderImage(); // without anti-aliasing and without multi-threading
        }

        camera.writeToImage();
    }

    @Test
    void testSunSet() {
        // Create the sunset picture without AA and without MT
        //createSunsetPic(false, false);//10 שניות

        // Create the sunset picture with AA and without MT
        createSunsetPic(true, false);// דקה ו30 שניות

        // Create the sunset picture with AA and with MT

        //createSunsetPic(true, true); // 38 שניות
    }
}
