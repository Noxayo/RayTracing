package org.example.geometry;

import org.example.math.Color;
import org.example.math.Point;

/**
 * Représente un triangle défini par 3 sommets
 * Format dans le fichier: tri indice1 indice2 indice3
 */
public class Triangle extends Shape {
    private Point v0;  // Premier sommet
    private Point v1;  // Deuxième sommet
    private Point v2;  // Troisième sommet

    /**
     * Constructeur
     * @param v0 Premier sommet
     * @param v1 Deuxième sommet
     * @param v2 Troisième sommet
     * @param diffuse La couleur diffuse
     * @param specular La couleur spéculaire
     */
    public Triangle(Point v0, Point v1, Point v2, Color diffuse, Color specular) {
        super(diffuse, specular);
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
    }

    // ========== GETTERS ==========

    /**
     * Premier sommet du triangle.
     */
    public Point getV0() {
        return v0;
    }

    /**
     * Deuxième sommet du triangle.
     */
    public Point getV1() {
        return v1;
    }

    /**
     * Troisième sommet du triangle.
     */
    public Point getV2() {
        return v2;
    }

    // ========== SETTERS ==========

    /**
     * Définit le premier sommet.
     */
    public void setV0(Point v0) {
        this.v0 = v0;
    }

    /**
     * Définit le deuxième sommet.
     */
    public void setV1(Point v1) {
        this.v1 = v1;
    }

    /**
     * Définit le troisième sommet.
     */
    public void setV2(Point v2) {
        this.v2 = v2;
    }

    @Override
    public String toString() {
        return "Triangle{v0=" + v0 +
                ", v1=" + v1 +
                ", v2=" + v2 +
                ", diffuse=" + diffuse +
                ", specular=" + specular + "}";
    }

    @Override
    /**
     * Intersection rayon-triangle (déléguée à Intersection).
     */
    public org.example.raytracer.Intersection intersect(org.example.raytracer.Ray ray) {
        // Délègue au helper centralisé dans Intersection
        return org.example.raytracer.Intersection.intersectTriangle(this, ray);
    }
}
