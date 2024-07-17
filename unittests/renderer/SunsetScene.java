package renderer;

import static java.awt.Color.*;

import geometries.Cylinder;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;
import lighting.DirectionalLight;

/** Tests for creating a sunset scene using triangles and a sphere for the sun */
public class SunsetScene {

    private static final Color SUN_COLOR = new Color(255, 69, 0); // Orange-red color for the sun
    private static final Color HORIZON_COLOR = new Color(255, 140, 0); // Darker orange color for the horizon
    private static final Color DARK_BROWN = new Color(120, 66, 18); // Darker brown color for the mountains
    private static final Color LIGHT_BROWN = new Color(210, 180, 140); // Lighter brown color for the mountains
    private static final Color CLOUD_COLOR = new Color(255, 100, 50); // White color for the clouds
    private static final Color TREE_GREEN = new Color(34, 139, 34); // Green color for the trees
    private static final Color TREE_BROWN = new Color(139, 69, 19); // Brown color for the tree trunks
    private static final Color ROCK_COLOR = new Color(112, 128, 144); // Grey color for the rock

    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Sunset Scene");
    /**
     * Camera builder for the tests
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setRayTracer(new SimpleRayTracer(scene));

    /**
     * Creates a sunset using triangles and a sphere for the sun
     *
     * @param x      The x-coordinate of the center of the sunset
     * @param y      The y-coordinate of the center of the sunset
     * @param z      The z-coordinate of the center of the sunset
     * @param size   The size of the sunset elements
     * @return A Geometries object representing the sunset
     */
    private Geometries createSunset(double x, double y, double z, double size) {
        return new Geometries(
                // Sun (as a sphere)
                new Sphere(new Point(x, y, z), size / 3) // smaller sun
                        .setEmission(SUN_COLOR)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),

                // Horizon
                new Triangle(new Point(x - size * 2, y - size / 4, z), new Point(x + size * 2, y - size / 4, z), new Point(x, y - size / 2, z))
                        .setEmission(HORIZON_COLOR)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),
                new Triangle(new Point(x - size * 2, y - size / 2, z), new Point(x + size * 2, y - size / 2, z), new Point(x, y - size / 4, z))
                        .setEmission(HORIZON_COLOR)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),
                new Triangle(new Point(x - size * 2, y - size / 6, z), new Point(x + size * 2, y - size / 6, z), new Point(x, y - size / 2, z))
                        .setEmission(HORIZON_COLOR)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),

                // Sky
                new Triangle(new Point(x - size * 3, y + size, z), new Point(x + size * 3, y + size, z), new Point(x, y, z))
                        .setEmission(new Color(300,100,50))
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(200)),
                new Triangle(new Point(x - size * 3, y + size / 2, z), new Point(x + size * 3, y + size / 2, z), new Point(x, y - size / 4, z))
                        .setEmission(new Color(400,80,50))
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),

                // Water with reflection of the sunset
                new Triangle(new Point(x - size * 3, y - size, z), new Point(x + size * 3, y - size, z), new Point(x, y - size / 2, z))
                        .setEmission(new Color(249,174,24))
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKt(0.5)),
                new Triangle(new Point(x - size * 3, y - size * 1.5, z), new Point(x + size * 3, y - size * 1.5, z), new Point(x, y - size, z))
                        .setEmission(new Color(200,160,20))
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKt(0.5)),
                new Triangle(new Point(x - size * 3, y - size * 3, z), new Point(x + size * 3, y - size * 3, z), new Point(x, y - size, z))
                        .setEmission(new Color(150,150,15))
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKt(0.5)),
                //לא רואים
                // Mountains
                new Triangle(new Point(x - size * 3, y - size * 1.5, z), new Point(x - size * 2, y - size * 2, z), new Point(x - size * 2.5, y - size * 1, z))
                        .setEmission(DARK_BROWN)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),
                new Triangle(new Point(x + size * 3, y - size * 1.5, z), new Point(x + size * 2, y - size * 2, z), new Point(x + size * 2.5, y - size * 1, z))
                        .setEmission(DARK_BROWN)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),
                new Triangle(new Point(x - size * 2.5, y - size * 1.8, z), new Point(x + size * 2.5, y - size * 1.8, z), new Point(x, y - size * 0.8, z))
                        .setEmission(LIGHT_BROWN)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),
                new Triangle(new Point(x - size * 2, y - size * 2.2, z), new Point(x + size * 2, y - size * 2.2, z), new Point(x, y - size, z))
                        .setEmission(LIGHT_BROWN)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100))
        );
    }

    /**
     * Creates clouds using spheres
     *
     * @param x The x-coordinate of the center of the cloud
     * @param y The y-coordinate of the center of the cloud
     * @param z The z-coordinate of the center of the cloud
     * @param size The size of the cloud elements
     * @return A Geometries object representing the cloud
     */
    private Geometries createCloud(double x, double y, double z, double size) {
        return new Geometries(
                new Sphere(new Point(x, y, z), size / 2)
                        .setEmission(CLOUD_COLOR)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKt(0.3)),
                new Sphere(new Point(x - size / 2, y, z), size / 2.5)
                        .setEmission(CLOUD_COLOR)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100).setKt(0.3)),
                new Sphere(new Point(x + size / 2, y, z), size / 2.5)
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
     * Creates a tree using triangles for leaves and a cylinder for the trunk
     *
     * @param x      The x-coordinate of the tree
     * @param y      The y-coordinate of the tree
     * @param z      The z-coordinate of the tree
     * @param height The height of the tree
     * @return A Geometries object representing the tree
     */
    private Geometries createTree(double x, double y, double z, double height) {
        return new Geometries(
                // Tree levels
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

    /**
     * Creates a rock with three trees on top of it
     *
     * @param x      The x-coordinate of the rock
     * @param y      The y-coordinate of the rock
     * @param z      The z-coordinate of the rock
     * @param size   The size of the rock
     * @return A Geometries object representing the rock with trees
     */
    private Geometries createRockWithTrees(double x, double y, double z, double size) {
        return new Geometries(
                // Rock
                new Sphere(new Point(x, y, z), size)
                        .setEmission(ROCK_COLOR)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),

                // Trees on the rock
                createTree(x - size / 2, y + size, z, size / 2),
                createTree(x + size / 2, y + size, z, size / 2),
                createTree(x, y + size * 1.5, z, size / 2)
        );
    }

    @Test
    public void createSunsetScene() {
        // Set background color
        scene.setBackground(new Color(17, 65, 97));

        // Add the sunset to the scene
        scene.geometries.add(createSunset(0, 0, -100, 50));

        // Add clouds to the scene
        scene.geometries.add(createCloud(-30, 50, -200, 30));
        scene.geometries.add(createCloud(50, 70, -100, 35));
        scene.geometries.add(createCloud(0, 40, -100, 25));


        // Add rock with trees to the scene
        scene.geometries.add(createRockWithTrees(0, -50, -150, 30));

        // Add lights to the scene
        scene.lights.add(new DirectionalLight(new Color(255, 140, 0), new Vector(-1, -1, -1)));
        //scene.lights.add(new AmbientLight(new Color(255, 255, 255), new Double3(0.1))); // Added Ambient Light

        // Set up the camera and render the image
        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("SunsetSceneWithCloudsAndRockWithTrees", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }
}
