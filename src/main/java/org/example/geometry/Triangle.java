package org.example.geometry;

import org.example.Color;
import org.example.Point;

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

    public Point getV0() {
        return v0;
    }

    public Point getV1() {
        return v1;
    }

    public Point getV2() {
        return v2;
    }

    // ========== SETTERS ==========

    public void setV0(Point v0) {
        this.v0 = v0;
    }

    public void setV1(Point v1) {
        this.v1 = v1;
    }

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
}
