package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Represents ambient light in 3D space.
 * Ambient light illuminates all objects uniformly.
 */
public class AmbientLight extends Light {

    /**
     * A predefined ambient light with no effect (black color).
     */
    static public AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    /**
     * Constructs an ambient light with a given intensity and coefficient.
     *
     * @param Ia The intensity of the ambient light, represented as a color.
     * @param Ka The coefficient for the ambient light, represented as a vector of doubles.
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * Constructs an ambient light with a given intensity and coefficient.
     *
     * @param Ia  The intensity of the ambient light, represented as a color.
     * @param Kad The coefficient for the ambient light, represented as a double.
     */
    public AmbientLight(Color Ia, double Kad) {
        super(Ia.scale(Kad));
    }

}
