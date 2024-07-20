package renderer;

import geometries.Cylinder;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;
import lighting.DirectionalLight;
import lighting.SpotLight;
import lighting.AmbientLight;

public class SunsetScene {

    private static final Color SUN_COLOR = new Color(255, 69, 0);
    private static final Color CLOUD_COLOR = new Color(400, 100, 50);
    private static final Color TREE_GREEN = new Color(50, 50, 10);
    private static final Color TREE_BROWN = new Color(139, 69, 19);

    private final Scene scene = new Scene("Combined Sunset and Trees Scene");
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setRayTracer(new SimpleRayTracer(scene));

    private Geometries createSunset(double x, double y, double z, double size) {
        return new Geometries(
                // Sun (as a sphere)
                new Sphere(new Point(x, y, z), size / 3.5)
                        .setEmission(SUN_COLOR)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),

                // Sky
                new Triangle(new Point(x - size * 3, y + size, z), new Point(x + size * 3, y + size, z), new Point(x, y, z))
                        .setEmission(new Color(300, 100, 50))
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(200)),
                new Triangle(new Point(x - size * 3, y + size / 2, z), new Point(x + size * 3, y + size / 2, z), new Point(x, y - size / 4, z))
                        .setEmission(new Color(400, 80, 50))
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),
                new Triangle(new Point(x - size * 3, y + size / 3, z), new Point(x + size * 3, y + size / 3, z), new Point(x, y - size / 4, z))
                        .setEmission(new Color(500, 80, 50))
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100))
        );
    }

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

    private Geometries createTree(double x, double y, double z, double height) {
        return new Geometries(
                new Triangle(new Point(x - 10, y - (height * 0.3), z), new Point(x + 12, y - (height * 0.3), z), new Point(x, y, z))
                        .setEmission(TREE_GREEN)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),
                new Triangle(new Point(x - 20, y - (height * 0.6), z - 10), new Point(x + 20, y - (height * 0.6), z - 10), new Point(x + 3, y - 5, z - 10))
                        .setEmission(TREE_GREEN)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),
                new Triangle(new Point(x - 25, y - (height * 0.85), z - 20), new Point(x + 25, y - (height * 0.85), z - 20), new Point(x + 5, y - 10, z - 20))
                        .setEmission(TREE_GREEN)
                        .setMaterial(new Material().setKd(0.6).setKs(0.1).setShininess(100)),
                new Cylinder(4, new Ray(new Point(x, y - height - 5, z - 30), new Vector(0, 1, 0)), (height * 0.3))
                        .setEmission(TREE_BROWN)
                        .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10))
        );
    }

    @Test
    public void createCombinedScene() {
        scene.setBackground(new Color(20, 70, 100));

        // Add the sunset to the scene (back layer)
        scene.geometries.add(createSunset(0, 0, -150, 75));

        // Add more triangles to the sky
        double size = 70;
        double x = 0;
        double y = 0;
        double z = -100;
        scene.geometries.add(
                new Triangle(new Point(x - size * 3, y + size * 1.5, z), new Point(x + size * 3, y + size * 1.5, z), new Point(x, y + size * 0.5, z))
                        .setEmission(new Color(300, 140, 50))
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(200)),
                new Triangle(new Point(x - size * 3, y + size * 2, z), new Point(x + size * 3, y + size * 2, z), new Point(x, y + size, z))
                        .setEmission(new Color(350, 140, 100))
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(200))
        );

        // Add trees to the scene (middle-back layer)
        scene.geometries.add(createTree(-150, -26, -900, 50));
        scene.geometries.add(createTree(-100, -19, -900, 50));
        scene.geometries.add(createTree(-50, -11, -900, 50));
        scene.geometries.add(createTree(50, -15, -900, 50));
        scene.geometries.add(createTree(100, -19, -900, 50));
        scene.geometries.add(createTree(150, -29, -900, 50));

        // Add clouds to the scene (middle layer)
        scene.geometries.add(createCloud(-30, 50, -200, 50));
        scene.geometries.add(createCloud(50, 70, -100, 35));
        scene.geometries.add(createCloud(0, 40, -100, 25));

        // Add more clouds to the scene (front layer)
        scene.geometries.add(createCloud(-80, 30, -50, 20));
        scene.geometries.add(createCloud(80, 60, -50, 25));
        scene.geometries.add(createCloud(20, 20, -50, 15));

        // Add water reflection of the sunset
        scene.geometries.add(new Triangle(new Point(x - size * 3.5, y - size, z),
                        new Point(x + size * 3.5, y - size, z),
                        new Point(x, y - size / 2, z))
                        .setEmission(new Color(500, 100, 50))
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKt(0.5)),

                new Triangle(new Point(x - size * 3.5, y - size * 1.25, z),
                        new Point(x + size * 3.5, y - size * 1.25, z),
                        new Point(x, y - size * 0.75, z))
                        .setEmission(new Color(300, 80, 50))
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKt(0.5)),

                new Triangle(new Point(x - size * 3.5, y - size * 1.5, z),
                        new Point(x + size * 3.5, y - size * 1.5, z),
                        new Point(x, y - size, z))
                        .setEmission(new Color(200, 80, 50))
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKt(0.5)),

                new Triangle(new Point(x - size * 3.5, y - size * 1.75, z),
                        new Point(x + size * 3.5, y - size * 1.75, z),
                        new Point(x, y - size * 1.25, z))
                        .setEmission(new Color(100, 80, 50))
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKt(0.5))

        );

        // Add lights to the scene
        scene.lights.add(new DirectionalLight(new Color(255, 140, 0), new Vector(-1, -1, -1)));
        //scene.lights.add(new AmbientLight(new Color(255, 255, 255), new Double3(0.1))); // Added Ambient Light

        // Set up the camera and render the image
        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("CombinedSunsetAndTreesScene", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }
}
