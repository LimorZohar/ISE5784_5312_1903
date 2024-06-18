package lighting;

import primitives.*;

public class AmbientLight {
    public static AmbientLight None;

    public AmbientLight(Color intensity, Double3 double3) {
        this.intensity = intensity;
    }
}
