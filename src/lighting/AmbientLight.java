package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Ambient Light for all object in 3D space
 * this class represented us the Ambient Light (תאורה סביבתית)
 */
public class AmbientLight {

    private final Color _intensity;

    /**
     * Field is initialized to defa  ult - the color black
     */
    static public AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    /**
     * constructor for knowing the intensity after the light factor
     *
     * @param Ia - Light illumination (RGB)
     * @param Ka - Light factor
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        //calculation of the intensity after the light factor//
        this._intensity = Ia.scale(Ka);
    }

    /**
     * constructor for knowing the intensity after the light factor
     *
     * @param Ia  - Light illumination (RGB)
     * @param Kad - Light factor in Double type
     */
    public AmbientLight(Color Ia, double Kad) {
        //calculation of the intensity after the light factor//
        this._intensity = Ia.scale(Kad);
    }

    /**
     * getter for intensity
     *
     * @return the intensity
     */
    public Color getIntensity() {
        return _intensity;
    }
}
