package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Ambient Light for all objects in 3D space.
 * This class represents the ambient light (תאורה סביבתית).
 */
public class AmbientLight {

    /**
     * The intensity of the ambient light.
     */
    private final Color _intensity;

    /**
     * Field is initialized to default - the color black.
     */
    static public AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    /**
     * Constructor for calculating the intensity after applying the light factor.
     *
     * @param Ia Light illumination (RGB).
     * @param Ka Light factor.
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        // Calculation of the intensity after applying the light factor.
        this._intensity = Ia.scale(Ka);
    }

    /**
     * Constructor for calculating the intensity after applying the light factor.
     *
     * @param Ia  Light illumination (RGB).
     * @param Kad Light factor as a double.
     */
    public AmbientLight(Color Ia, double Kad) {
        // Calculation of the intensity after applying the light factor.
        this._intensity = Ia.scale(Kad);
    }

    /**
     * Getter for the intensity.
     *
     * @return The intensity of the ambient light.
     */
    public Color getIntensity() {
        return _intensity;
    }
}
