package org.example.raytracer;

import org.example.geometry.Shape;

public class Intersection {
    private final Shape shape;
    private final double t;
    private final double[] point;
    private final double[] normal; // normalized

    public Intersection(Shape shape, double t, double[] point, double[] normal) {
        this.shape = shape;
        this.t = t;
        this.point = point;
        this.normal = normal;
    }

    public Shape getShape() { return shape; }
    public double getT() { return t; }
    public double[] getPoint() { return point; }
    public double[] getNormal() { return normal; }

    // Compute color at the intersection according to scene lighting
    public org.example.Color computeColor(org.example.Scene scene, double[] eye) {
        org.example.AbstractVec3 V = new org.example.AbstractVec3();
        double[] N = normal;
        double[] viewDir = V.normalization(V.subtraction(eye, point));

        double[] baseArr = shape.getDiffuse().getColor();
        double[] specArr = shape.getSpecular().getColor();
        double shininess = shape.getShininess();

        double[] ambArr = scene.getAmbient().getColor();
        double r = clamp01(ambArr[0] * baseArr[0]);
        double g = clamp01(ambArr[1] * baseArr[1]);
        double b = clamp01(ambArr[2] * baseArr[2]);

        for (org.example.raytracer.AbstractLight light : scene.getLights()) {
            double[] L;
            double[] lightColor = light.getColor().getColor();
            double maxDist = Double.POSITIVE_INFINITY;
            if (light instanceof org.example.raytracer.DirectionalLight dl) {
                double[] dirL = dl.getDirection().getVector();
                L = V.normalization(V.multiplicationByScalar(-1.0, dirL));
            } else if (light instanceof org.example.raytracer.PointLight pl) {
                double[] lp = pl.getPosition().getPoint();
                double[] toLight = V.subtraction(lp, point);
                maxDist = V.length(toLight);
                L = V.normalization(toLight);
            } else {
                continue;
            }

            // Shadow ray: offset point slightly along normal to avoid self-intersection
            double[] shadowOrigin = V.addition(point, V.multiplicationByScalar(1e-4, N));
            org.example.raytracer.Ray shadowRay = new org.example.raytracer.Ray(shadowOrigin, L);
            if (isOccluded(scene, shadowRay, maxDist)) {
                continue; // in shadow for this light
            }

            double ndotl = Math.max(0.0, V.scalarProduct(N, L));
            r = clamp01(r + baseArr[0] * lightColor[0] * ndotl);
            g = clamp01(g + baseArr[1] * lightColor[1] * ndotl);
            b = clamp01(b + baseArr[2] * lightColor[2] * ndotl);

            // Specular (Phong)
            if (shininess > 0) {
                double[] minusL = V.multiplicationByScalar(-1.0, L);
                double[] R = reflect(minusL, N, V);
                double rv = Math.max(0.0, V.scalarProduct(R, viewDir));
                double spec = Math.pow(rv, shininess);
                r = clamp01(r + specArr[0] * lightColor[0] * spec);
                g = clamp01(g + specArr[1] * lightColor[1] * spec);
                b = clamp01(b + specArr[2] * lightColor[2] * spec);
            }
        }

        return new org.example.Color(r, g, b);
    }

    private boolean isOccluded(org.example.Scene scene, org.example.raytracer.Ray ray, double maxDist) {
        org.example.AbstractVec3 V = new org.example.AbstractVec3();
        for (Shape s : scene.getShapes()) {
            Intersection h = s.intersect(ray);
            if (h != null) {
                if (Double.isInfinite(maxDist) || h.t < maxDist - 1e-4) {
                    return true;
                }
            }
        }
        return false;
    }

    private double[] reflect(double[] I, double[] N, org.example.AbstractVec3 V) {
        double dot = V.scalarProduct(I, N);
        double[] twoN = V.multiplicationByScalar(2.0 * dot, N);
        return V.subtraction(I, twoN);
    }

    private double clamp01(double v) {
        if (v < 0) return 0;
        if (v > 1) return 1;
        return v;
    }
}
