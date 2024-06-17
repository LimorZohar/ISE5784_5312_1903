package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.*;

public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = AmbientLight.None;
    public Geometries geometries = new Geometries();

    public Scene(String name) {
        this.name = name;
    }
}
