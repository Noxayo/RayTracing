package org.example.raytracer;

import org.example.math.Color;
import org.example.math.Point;

/**
 * Lumière ponctuelle (comme une ampoule)
 * La lumière vient d'un point précis dans l'espace
 * Format dans le fichier: point x y z r g b
 */
public class PointLight extends AbstractLight {
    private Point position;  // Position de la lumière

    /**
     * Constructeur
     * @param position La position de la lumière (point)
     * @param color La couleur de la lumière
     */
    public PointLight(Point position, Color color) {
        super(color);
        this.position = position;
    }

    // ========== GETTERS ==========

    /**
     * Retourne la position de la lumière.
     */
    public Point getPosition() {
        return position;
    }

    // ========== SETTERS ==========

    /**
     * Définit la position de la lumière.
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "PointLight{position=" + position +
                ", color=" + color + "}";
    }
}