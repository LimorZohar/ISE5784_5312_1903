package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Scene class represents a scene in 3D space containing various geometries and lighting conditions.
 * This class uses method chaining for setting up the scene properties.
 */
public class Scene {
    /**
     * The name of the scene.
     */
    public final String name;

    /**
     * The background color of the scene. Default is black.
     */
    public Color background = Color.BLACK;

    /**
     * The ambient light of the scene. Default is no ambient light.
     */
    public AmbientLight ambientLight = AmbientLight.NONE;

    /**
     * The collection of geometries in the scene.
     */
    public Geometries geometries = new Geometries();

    /**
     * The list of light sources in the scene.
     */
    public List<LightSource> lights = new LinkedList<>();

    /**
     * Constructs a Scene object with the given name.
     *
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Sets the background color of the scene.
     *
     * @param background The background color to set.
     * @return The current scene object (this) for method chaining.
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight The ambient light to set.
     * @return The current scene object (this) for method chaining.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries of the scene.
     *
     * @param geometries The geometries to set.
     * @return The current scene object (this) for method chaining.
     * @throws IllegalArgumentException If the geometries parameter is null.
     */
    public Scene setGeometries(Geometries geometries) {
        if (geometries == null)
            throw new IllegalArgumentException("Geometries cannot be null.");
        this.geometries = geometries;
        return this;
    }

    /**
     * Sets the light sources of the scene.
     *
     * @param lights The list of light sources to set.
     * @return The current scene object (this) for method chaining.
     */
    public Scene setLights(List<LightSource> lights) {
        if (lights != null)
            this.lights = lights;
        return this;
    }
}
