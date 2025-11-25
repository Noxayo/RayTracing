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
}