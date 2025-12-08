package org.example.geometry;

import org.example.Color;
import org.example.Point;
import org.example.Vector;

/**
 * Représente un plan infini
 * Format dans le fichier: plane x y z nx ny nz
 */
public class Plane extends Shape {
    private Point point;    // Un point du plan
    private Vector normal;  // Vecteur normal au plan

    /**
     * Constructeur
     * @param point Un point appartenant au plan
     * @param normal Le vecteur normal au plan
     * @param diffuse La couleur diffuse
     * @param specular La couleur spéculaire
     */
    public Plane(Point point, Vector normal, Color diffuse, Color specular) {
        super(diffuse, specular);
        this.point = point;
        this.normal = normal;
    }

    // ========== GETTERS ==========

    public Point getPoint() {
        return point;
    }

    public Vector getNormal() {
        return normal;
    }

    // ========== SETTERS ==========

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setNormal(Vector normal) {
        this.normal = normal;
    }

    @Override
    public String toString() {
        return "Plane{point=" + point +
                ", normal=" + normal +
                ", diffuse=" + diffuse +
                ", specular=" + specular + "}";
    }

    @Override
    public org.example.raytracer.Intersection intersect(org.example.raytracer.Ray ray) {
        org.example.AbstractVec3 V = new org.example.AbstractVec3();
        double[] origin = ray.getOrigin();
        double[] dir = ray.getDirection();
        double[] P0 = point.getPoint();
        double[] N = normal.getVector();

        double denom = V.scalarProduct(N, dir);
        if (Math.abs(denom) < 1e-8) return null; // parallel
        double t = V.scalarProduct(V.subtraction(P0, origin), N) / denom;
        if (t <= 1e-6) return null;
        double[] hitPoint = V.addition(origin, V.multiplicationByScalar(t, dir));
        double[] n = V.normalization(N);
        return new org.example.raytracer.Intersection(this, t, hitPoint, n);
    }
}