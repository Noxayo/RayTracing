package org.example.geometry;

import org.example.math.Color;
import org.example.math.Point;
import org.example.math.Vector;

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

    /**
     * Retourne un point appartenant au plan.
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Retourne la normale du plan (non normalisée).
     */
    public Vector getNormal() {
        return normal;
    }

    // ========== SETTERS ==========

    /**
     * Définit un point du plan.
     */
    public void setPoint(Point point) {
        this.point = point;
    }

    /**
     * Définit la normale du plan.
     */
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
    /**
     * Intersection rayon-plan (déléguée à Intersection).
     */
    public org.example.raytracer.Intersection intersect(org.example.raytracer.Ray ray) {
        return org.example.raytracer.Intersection.intersectPlane(this, ray);
    }
}