package renderer;

import geometries.Intersectable;
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

    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

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
    private Color calcColor(GeoPoint point, Ray ray, int level, Double3 k) {
        Color color = point.geometry.getEmission();
        color = color.add(calcLocalEffects(point, ray));

        if (level == 1) {
            return color;
        }

        return color.add(calcGlobalEffects(point, ray, level, k));
    }

    @Override
    public Color traceRay(Ray ray) {
        var intersection = ray.findClosestGeoPoint(this.scene.geometries.findGeoIntersections(ray));
        return intersection == null ? scene.background : calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, Double3.ONE);
    }

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
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, double nShininess, Color lightIntensity) {
        double minusVR = alignZero(-v.dotProduct(l.subtract(n.scale(l.dotProduct(n)).scale(2))));
        return minusVR <= 0 ? Color.BLACK : lightIntensity.scale(ks.scale(Math.pow(minusVR, nShininess)));
    }

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


    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        var intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null || intersections.isEmpty()) return Double3.ONE;

        Double3 ktr = Double3.ONE;
        for (GeoPoint gp : intersections) {
            ktr = ktr.product(gp.geometry.getMaterial().kT);
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
        }
        return ktr;
    }

    private Ray constructReflectedRay(Point point, Vector v, Vector n) {
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(point, r, n);
    }

    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

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
