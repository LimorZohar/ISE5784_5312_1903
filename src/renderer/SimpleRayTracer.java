package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * A class that inherits from the RayTracerBase class and implements the method
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * The maximum recursion level for calculating color.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * The minimum value for the attenuation factor to continue recursion.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * Constructor
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
     * @param level recursion level
     * @param k     attenuation factor
     * @return Color of the intersection point
     */
    private Color calcColor(GeoPoint point, Ray ray, int level, Double3 k) {
        Color color = point.geometry.getEmission();
        color = color.add(calcLocalEffects(point, ray));

        if (level == 1) {
            return color;
        }

        return color.add(calcGlobalEffects(point, ray, level, k));
    }

    /**
     * Calculates the color at an intersection point with maximum recursion level.
     * Adds the scene's ambient light intensity to the calculated color.
     *
     * @param point the intersection point
     * @param ray   the intersecting ray
     * @return the calculated color at the intersection point including ambient light
     */
    private Color calcColor(GeoPoint point, Ray ray) {
        return calcColor(point, ray, MAX_CALC_COLOR_LEVEL, Double3.ONE)
                .add(this.scene.ambientLight.getIntensity());
    }

    @Override
    public Color traceRay(Ray ray) {
        var intersection = ray.findClosestGeoPoint(this.scene.geometries.findGeoIntersections(ray));
        return intersection == null ? scene.background : calcColor(intersection, ray);
        //calcColor(intersection, ray,
        // MAX_CALC_COLOR_LEVEL, Double3.ONE);
    }

    /**
     * Calculate the local effects of lighting at an intersection point
     *
     * @param intersection the intersection point
     * @param ray          the ray that intersected
     * @return the color resulting from the local effects
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

            if (nl * nv > 0 && unshaded(intersection, lightSource, l, n)) { // check for shadow
                Double3 ktr = transparency(intersection, lightSource, l, n);
                Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
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
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, double nShininess,
                               Color lightIntensity) {
        double minusVR = alignZero(-v.dotProduct(l.subtract(n.scale(l.dotProduct(n))
                .scale(2))));
        return minusVR <= 0 ? Color.BLACK : lightIntensity.scale(ks.scale(Math.pow(minusVR, nShininess)));
    }

    /**
     * Check if a point is unshaded by any geometry blocking the light source
     *
     * @param geoPoint the point to check
     * @param light    the light source
     * @param l        the light direction
     * @param n        the normal at the point
     * @return true if the point is unshaded, false otherwise
     */
    private boolean unshaded(GeoPoint geoPoint, LightSource light, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        double lightDistance = light.getDistance(geoPoint.point);

        var intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) {
            return true;
        }

        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
                if (gp.geometry.getMaterial().kT.equals(Double3.ZERO)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Calculates the transparency factor for a point with respect to a light source.
     *
     * @param gp    The point for which transparency is calculated.
     * @param light The light source.
     * @param l     The vector from the light source to the point.
     * @param n     The normal vector at the point.
     * @return The transparency factor as a Double3 representing (r, g, b) values.
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n); //build ray with delta
        double lightDistance = light.getDistance(gp.point);

        var intersections = this.scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) {
            return Double3.ONE; //no intersections
        }
        Double3 ktr = Double3.ONE;
        for (GeoPoint geoPoint : intersections) {
            if (alignZero(geoPoint.point.distance(gp.point) - lightDistance) <= 0) {
                ktr = ktr.product(geoPoint.geometry.getMaterial().kT); //the more transparency the less shadow
                if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
            }
        }
        return ktr;
    }

    /**
     * Construct a reflected ray from a point
     *
     * @param point the point of reflection
     * @param v     the direction vector of the incoming ray
     * @param n     the normal at the point
     * @return the reflected ray
     */
    private Ray constructReflectedRay(Point point, Vector v, Vector n) {
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(point, r, n);
    }

    /**
     * Construct a refracted ray from a point
     *
     * @param point the point of refraction
     * @param v     the direction vector of the incoming ray
     * @param n     the normal at the point
     * @return the refracted ray
     */
    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    /**
     * Calculate the global effects (reflection and refraction) of lighting at an intersection point
     *
     * @param gp    the intersection point
     * @param ray   the incoming ray
     * @param level the recursion level
     * @param k     the attenuation factor
     * @return the color resulting from the global effects
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        Material material = gp.geometry.getMaterial();

        Double3 kr = material.kR;
        Double3 kkr = kr.product(k);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            Ray reflectedRay = constructReflectedRay(gp.point, v, n);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null) {
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            }
        }

        Double3 kt = material.kT;
        Double3 kkt = kt.product(k);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            Ray refractedRay = constructRefractedRay(gp.point, v, n);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null) {
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
            }
        }

        return color;
    }

    /**
     * Finds the closest intersection point of a ray with the scene geometries.
     *
     * @param ray The ray to intersect with the geometries.
     * @return The closest intersection point as a GeoPoint, or null if there are no intersections.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(intersections);
    }
}