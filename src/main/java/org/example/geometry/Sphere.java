package org.example.geometry;

import org.example.Color;
import org.example.Point;

/**
 * Représente une sphère
 * Format dans le fichier: sphere x y z rayon
 * PRIORITAIRE pour le projet
 */
public class Sphere extends Shape {
    private Point center;   // Centre de la sphère
    private double radius;  // Rayon de la sphère

    /**
     * Constructeur
     * @param center Le centre de la sphère
     * @param radius Le rayon
     * @param diffuse La couleur diffuse
     * @param specular La couleur spéculaire
     */
    public Sphere(Point center, double radius, Color diffuse, Color specular) {
        super(diffuse, specular);
        this.center = center;
        this.radius = radius;
    }

    // ========== GETTERS ==========

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    // ========== SETTERS ==========

    public void setCenter(Point center) {
        this.center = center;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Sphere{center=" + center +
                ", radius=" + radius +
                ", diffuse=" + diffuse +
                ", specular=" + specular + "}";
    }
}