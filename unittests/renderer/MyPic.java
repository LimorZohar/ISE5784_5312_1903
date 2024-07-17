package renderer;

import static java.awt.Color.*;

import geometries.Cylinder;
import geometries.Geometries;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class MyPic {
    private static final Color BROWN = new Color(139, 69, 19);
    private static final Color GREEN = new Color(50, 1, 1);

    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setRayTracer(new SimpleRayTracer(scene));

    /**
     * Creates a tree at the specified coordinates.
     *
     * @param x      The x-coordinate of the tree.
     * @param y      The y-coordinate of the tree.
     * @param z      The z-coordinate of the tree.
     * @param height The height of the tree.
     * @return A Geometries object representing the tree.
     */
    private Geometries createTree(double x, double y, double z, double height) {
        return new Geometries(
                // Tree levels
                new Triangle(new Point(x - 10, y - (height * 0.3), z), new Point(x + 12, y - (height * 0.3), z), new Point(x, y, z))
                        .setEmission(GREEN)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),
                new Triangle(new Point(x - 20, y - (height * 0.6), z - 10), new Point(x + 20, y - (height * 0.6), z - 10), new Point(x + 3, y - 5, z - 10))
                        .setEmission(GREEN)
                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setShininess(100)),
                new Triangle(new Point(x - 25, y - (height * 0.85), z - 20), new Point(x + 25, y - (height * 0.85), z - 20), new Point(x + 5, y - 10, z - 20))
                        .setEmission(GREEN)
                        .setMaterial(new Material().setKd(0.6).setKs(0.1).setShininess(100)),
                new Cylinder(4, new Ray(new Point(x, y - height - 5, z - 30), new Vector(0, 1, 0)), (height * 0.3))
                        .setEmission(BROWN)
                        .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10))
        );
    }

    @Test
    public void createScene() {
        // Set background color
        scene.setBackground(new Color(17, 65, 97));

        // Add trees to the scene
        scene.geometries.add(createTree(-150, 0, -250, 50));
        scene.geometries.add(createTree(-100, 5, -250, 50));
        scene.geometries.add(createTree(-50, 7, -250, 50));
        scene.geometries.add(createTree(0, 30, -250, 50));
        scene.geometries.add(createTree(50, 40, -250, 50));
        scene.geometries.add(createTree(100, 0, -250, 50));
        scene.geometries.add(createTree(150, 10, -250, 50));

        // Add lights to the scene
        scene.lights.add(new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                .setKl(0.0004).setKq(0.0000006));

        // Set up the camera and render the image
        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("MyPic", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }
}
