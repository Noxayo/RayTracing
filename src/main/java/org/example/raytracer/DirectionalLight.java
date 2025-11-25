package org.example.raytracer;

import org.example.Color;
import org.example.Vector;

/**
 * Lumière directionnelle (comme le soleil)
 * La lumière vient d'une direction, pas d'un point précis
 * Format dans le fichier: directional x y z r g b
 */
public class DirectionalLight extends AbstractLight {
    private Vector direction;  // Direction de la lumière

    /**
     * Constructeur
     * @param direction La direction de la lumière (vecteur)
     * @param color La couleur de la lumière
     */
    public DirectionalLight(Vector direction, Color color) {
        super(color);
        this.direction = direction;
    }

    // ========== GETTERS ==========

    public Vector getDirection() {
        return direction;
    }

    // ========== SETTERS ==========

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "DirectionalLight{direction=" + direction +
                ", color=" + color + "}";
    }
}