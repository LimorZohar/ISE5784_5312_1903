package renderer;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import static primitives.Util.alignZero;

/**
 * A class that inherits from the RayTracerBase class and implements the method
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * constructor
     *
     * @param scene A scene where the department is initialized
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Get the color of an intersection point
     *
     * @param point point of intersection
     * @param ray   for the ray
     * @return Color of the intersection point
     */
    private Color calcColor(Intersectable.GeoPoint point, Ray ray) {
        return this.scene.ambientLight.getIntensity()
                .add(calcLocalEffects(point, ray));
    }

    @Override
    public Color traceRay(Ray ray) {
        var intersection = ray.findClosestGeoPoint(this.scene.geometries.findGeoIntersections(ray));
        return intersection == null ? scene.background : calcColor(intersection, ray);
    }

    /**
     * This method calculates the local effects (diffuse and specular) of lighting at a given intersection point.
     *
     * @param intersection The intersection point and geometry information.
     * @param ray          The ray that intersects with the geometry.
     * @return The color result of local lighting effects.
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Color color = intersection.geometry.getEmission();
        Vector v = ray.getDirection();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;

        Material material = intersection.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0) { // sign(nl) == sign(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(material.kD, l, n, lightIntensity),
                        calcSpecular(material.kS, l, n, v, material.nShininess, lightIntensity));
            }
        }
        return color;
    }

    /**
     * This method calculates the diffuse component of lighting at a given point.
     *
     * @param kd             The diffuse reflection coefficient.
     * @param l              The direction vector from the light source to the point.
     * @param n              The normal vector at the point.
     * @param lightIntensity The intensity of the light at the point.
     * @return The color result of the diffuse component.
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd.scale(Math.abs(l.dotProduct(n))));
    }

    /**
     * This method calculates the specular component of lighting at a given point.
     *
     * @param ks             The specular reflection coefficient.
     * @param l              The direction vector from the light source to the point.
     * @param n              The normal vector at the point.
     * @param v              The direction vector of the viewer (or camera).
     * @param nShininess     The shininess factor of the material.
     * @param lightIntensity The intensity of the light at the point.
     * @return The color result of the specular component.
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, double nShininess, Color lightIntensity) {
        double minusVR = alignZero(-v.dotProduct(l.subtract(n.scale(l.dotProduct(n)).scale(2))));
        return minusVR <= 0 ? Color.BLACK : lightIntensity.scale(ks.scale(Math.pow(minusVR, nShininess)));
    }

}
