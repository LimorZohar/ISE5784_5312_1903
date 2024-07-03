package primitives;

/**
 * Represents the material properties of a surface, including reflection and transparency coefficients.
 */
public class Material {
    /**
     * Diffuse reflection coefficient.
     */
    public Double3 kD = Double3.ZERO;

    /**
     * Specular reflection coefficient.
     */
    public Double3 kS = Double3.ZERO;

    /**
     * Transparency coefficient.
     */
    public Double3 kT = Double3.ZERO;

    /**
     * Reflection coefficient.
     */
    public Double3 kR = Double3.ZERO;

    /**
     * Shininess coefficient.
     */
    public int nShininess = 1;

    /**
     * Sets the diffuse reflection coefficient.
     *
     * @param kD The diffuse reflection coefficient.
     * @return The current Material object for method chaining.
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient.
     *
     * @param kD The diffuse reflection coefficient.
     * @return The current Material object for method chaining.
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Sets the specular reflection coefficient.
     *
     * @param kS The specular reflection coefficient.
     * @return The current Material object for method chaining.
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the specular reflection coefficient.
     *
     * @param kS The specular reflection coefficient.
     * @return The current Material object for method chaining.
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Sets the transparency coefficient.
     *
     * @param kT The transparency coefficient.
     * @return The current Material object for method chaining.
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Sets the transparency coefficient.
     *
     * @param kt The transparency coefficient.
     * @return The current Material object for method chaining.
     */
    public Material setKt(double kt) {
        this.kT = new Double3(kt);
        return this;
    }

    /**
     * Sets the reflection coefficient.
     *
     * @param kR The reflection coefficient.
     * @return The current Material object for method chaining.
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * Sets the reflection coefficient.
     *
     * @param kR The reflection coefficient.
     * @return The current Material object for method chaining.
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * Sets the shininess coefficient.
     *
     * @param nShininess The shininess coefficient.
     * @return The current Material object for method chaining.
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
